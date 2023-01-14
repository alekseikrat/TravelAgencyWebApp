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
import ua.edu.znu.travelagencyweb.model.Employee;
import ua.edu.znu.travelagencyweb.model.Tour;
import ua.edu.znu.travelagencyweb.service.EmployeeDaoImpl;
import ua.edu.znu.travelagencyweb.service.TourDaoImpl;

import java.io.IOException;
import java.util.List;

/**
 * Assigns tours to the selected employee.
 */
@WebServlet("/EmployeeAssignmentServlet")
public class EmployeeAssignmentServlet extends HttpServlet {
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
        String action = request.getParameter("action") == null
                ? (String) request.getAttribute("action")
                : request.getParameter("action");
        long employeeId = request.getParameter("employeeId") == null
                ? (long) request.getAttribute("employeeId")
                : Long.parseLong(request.getParameter("employeeId"));
        EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
        TourDaoImpl tourDao = new TourDaoImpl();
        Employee employee = employeeDao.findById(employeeId);
        switch (action) {
            case "employeeSelect" -> {
            }
            case "tourAssign" -> {
                long tourId = Long
                        .parseLong(request.getParameter("selectedTour"));
                Tour tour = tourDao.findById(tourId);
                tour.getEmployees().add(employee);
                tourDao.update(tour);
            } case "tourRemove" -> {
                Long tourId = Long
                        .valueOf(request.getParameter("selectedTour"));
                tourDao.removeEmployeeFromTour(tourId, employee);
            }
        }
        List<Tour> otherTours = tourDao.findAll();
        List<Tour> employeeTours = tourDao.findByEmployee(employee);
        otherTours.removeAll(employeeTours);
        context.setVariable("employee", employee);
        context.setVariable("employeeTours", employee.getTour());
        context.setVariable("otherTours", otherTours);
        templateEngine.process("employeeassignment", context, response.getWriter());
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
