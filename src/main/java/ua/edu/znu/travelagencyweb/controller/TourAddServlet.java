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
import ua.edu.znu.travelagencyweb.model.Tour;
import ua.edu.znu.travelagencyweb.service.TourDaoImpl;

import java.io.IOException;

/**
 * Adds a new tour.
 */
@WebServlet("/TourAddServlet")
public class TourAddServlet extends HttpServlet {
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
        templateEngine.process("touradd", context, response.getWriter());
    }
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {
        TourDaoImpl tourDao = new TourDaoImpl();
        Tour tour = new Tour();
        String tourDeparture = request.getParameter("tourDeparture");
        tour.setDeparture(tourDeparture);
        String tourArrival = request.getParameter("tourArrival");
        tour.setArrival(tourArrival);
        String tourTransport = request.getParameter("tourTransport");
        tour.setTransport(tourTransport);
        tourDao.create(tour);
        tour = tourDao.findByArrival(tourArrival);
        request.setAttribute("tourId", tour.getId());
        request.setAttribute("action","tourAdd");
        request.getRequestDispatcher("/TourAssignmentServlet")
                .forward(request, response);
    }
    private WebContext getWebContext(HttpServletRequest request,
                                     HttpServletResponse response) {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(request, response);
        return new WebContext(webExchange);
    }
}
