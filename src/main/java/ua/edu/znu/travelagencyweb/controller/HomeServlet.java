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
import ua.edu.znu.travelagencyweb.model.dto.TourAssignment;
import ua.edu.znu.travelagencyweb.service.ClientDaoImpl;
import ua.edu.znu.travelagencyweb.service.TourDaoImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The start point for the authenticated user.
 */
@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
    private TemplateEngine templateEngine;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.templateEngine = (TemplateEngine) getServletContext()
                .getAttribute(ThymeleafConfiguration.TEMPLATE_ENGINE_ATR);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws IOException {
        doPost(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
        String message = (String) request.getAttribute("message");
        WebContext context = getWebContext(request, response);
        TourDaoImpl tourDao = new TourDaoImpl();
        List<Tour> tours = tourDao.findAll();
        List<TourAssignment> tourAssignments = new ArrayList<>();
        for (Tour tour : tours) {
            TourAssignment tourAssignment = new TourAssignment();
            tourAssignment.setTourId(tour.getId());
            tourAssignment.setTourDeparture(tour.getDeparture());
            tourAssignment.setTourArrival(tour.getArrival());
            tourAssignment.setTourTransport(tour.getTransport());
            StringBuilder clientsInfo = new StringBuilder();
            ClientDaoImpl clientDao = new ClientDaoImpl();
            List<Client> tourClients = clientDao.findByTour(tour);
            for (Client client : tourClients) {
                clientsInfo.append(client.getSurname())
                        .append(" ")
                        .append(client.getName().charAt(0))
                        .append(". ")
                        .append(client.getAge())
                        .append(" років\n");
            }
            tourAssignment.setClientsInfo(clientsInfo.toString());
            tourAssignments.add(tourAssignment);
        }
        context.setVariable("tourAssignments", tourAssignments);
        context.setVariable("message", message);
        templateEngine.process("home", context, response.getWriter());
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
