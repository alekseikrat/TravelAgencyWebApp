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
import ua.edu.znu.travelagencyweb.service.EmployeeDaoImpl;

import java.io.IOException;

/**
 * Adds a new employee.
 */
@WebServlet("/EmployeeAddServlet")
public class EmployeeAddServlet extends HttpServlet {
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
        templateEngine.process("employeeadd", context, response.getWriter());
    }
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {
        EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
        Employee employee = new Employee();
        String employeeSurname = request.getParameter("employeeSurname");
        employee.setSurname(employeeSurname);
        String employeeName = request.getParameter("employeeName");
        employee.setName(employeeName);
        int employeeAge = Integer.parseInt(request.getParameter("employeeAge"));
        employee.setAge(employeeAge);
        employeeDao.create(employee);
        employee = employeeDao.findBySurname(employeeSurname);
        request.setAttribute("employeeId", employee.getId());
        request.setAttribute("action","employeeAdd");
        request.getRequestDispatcher("/EmployeesServlet")
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
