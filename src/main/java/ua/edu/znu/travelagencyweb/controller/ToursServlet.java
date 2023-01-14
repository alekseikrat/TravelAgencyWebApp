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
 * Provides operations with Tour directory.
 */
@WebServlet("/ToursServlet")
public class ToursServlet extends HttpServlet {
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
        WebContext context = getWebContext(request, response);
        TourDaoImpl tourDao = new TourDaoImpl();
        List<Tour> tours = tourDao.findAll();
        context.setVariable("tours", tours);
        templateEngine.process("tours", context, response.getWriter());
        response.setContentType("text/html;charset=UTF-8");
    }
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
        String action = request.getParameter("action");
        TourDaoImpl tourDao = new TourDaoImpl();
        long tourId = Long.parseLong(request.getParameter("tourId"));
        Tour tour = tourDao.findById(tourId);
        switch (action) {
            case "tourEdit" -> {
                String tourDeparture = request.getParameter("tourDeparture");
                tour.setDeparture(tourDeparture);
                String tourArrival = request.getParameter("tourArrival");
                tour.setArrival(tourArrival);
                String tourTransport = request.getParameter("tourTransport");
                tour.setTransport(tourTransport);
                tourDao.update(tour);
            }
            case "tourRemove" -> {
                /*You need delete the tour's clients first
due to bidirectional many-to-many association */
                ClientDaoImpl clientDao = new ClientDaoImpl();
                for (Client client : tour.getClients()) {
                    client.getTours().remove(tour);
                    clientDao.update(client);
                }
                tourDao.delete(tour);
            }
        }
        /*Make GET request to this servlet*/
        response.sendRedirect(request.getContextPath() + "/ToursServlet");
    }
    private WebContext getWebContext(HttpServletRequest request,
                                     HttpServletResponse response) {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(request, response);
        return new WebContext(webExchange);
    }
}
