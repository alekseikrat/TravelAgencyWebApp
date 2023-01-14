package ua.edu.znu.travelagencyweb.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import ua.edu.znu.travelagencyweb.model.Client;
import ua.edu.znu.travelagencyweb.model.Tour;
import ua.edu.znu.travelagencyweb.service.ClientDaoImpl;
import ua.edu.znu.travelagencyweb.service.TourDaoImpl;

import java.io.IOException;
import java.util.List;

/**
 * Assigns clients to the selected tour.
 */
@WebServlet("/TourAssignmentServlet")
public class TourAssignmentServlet extends HttpServlet{
    private TemplateEngine templateEngine;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.templateEngine = (TemplateEngine) getServletContext()
                .getAttribute(ThymeleafConfiguration.TEMPLATE_ENGINE_ATR);
    }
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {
        doPost(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
        WebContext context = getWebContext(request, response);
/* tourId passed as parameter from home.html and forwarded
as request attribute from TourAddServlet */
        String action = request.getParameter("action") == null
                ? (String) request.getAttribute("action")
                : request.getParameter("action");
        long tourId = request.getParameter("tourId") == null
                ? (long) request.getAttribute("tourId")
                : Long.parseLong(request.getParameter("tourId"));
        TourDaoImpl tourDao = new TourDaoImpl();
        ClientDaoImpl clientDao = new ClientDaoImpl();
        Tour tour = tourDao.findById(tourId);
        switch (action) {
            case "tourSelect" -> {
            }
            case "clientAssign" -> {
                long clientId = Long
                        .parseLong(request.getParameter("selectedClient"));
                Client client = clientDao.findById(clientId);
                client.getTours().add(tour);
                clientDao.update(client);
            } case "clientRemove" -> {
                Long clientId = Long
                        .valueOf(request.getParameter("selectedClient"));
                clientDao.removeTourFromClient(clientId, tour);
            }
        }
        List<Client> otherClients = clientDao.findAll();
        List<Client> tourClients = clientDao.findByTour(tour);
        otherClients.removeAll(tourClients);
        context.setVariable("tour", tour);
        context.setVariable("tourClients", tour.getClients());
        context.setVariable("otherClients", otherClients);
        templateEngine.process("tourassignment", context, response.getWriter());
        response.setContentType("text/html;charset=UTF-8");
    }
    private WebContext getWebContext(HttpServletRequest request,
                                     HttpServletResponse response) {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(request, response);
        return new WebContext(webExchange);
    }
}
