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
import java.util.List;

/**
 * Provides operations with Employee directory.
 */
@WebServlet("/EmployeesServlet")
public class EmployeesServlet extends HttpServlet {
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
        EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
        List<Employee> employees = employeeDao.findAll();
        context.setVariable("employees", employees);
        templateEngine.process("employees", context, response.getWriter());
        response.setContentType("text/html;charset=UTF-8");
    }
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
        String action = request.getParameter("action");
        EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
        long employeeId = Long.parseLong(request.getParameter("employeeId"));
        Employee employee = employeeDao.findById(employeeId);
        switch (action) {
            case "employeeEdit" -> {
                String employeeSurname = request.getParameter("employeeSurname");
                employee.setSurname(employeeSurname);
                String employeeName = request.getParameter("employeeName");
                employee.setName(employeeName);
                int employeeAge = Integer.parseInt(request.getParameter("employeeAge"));
                employee.setAge(employeeAge);
                employeeDao.update(employee);
            }
            case "employeeRemove" -> employeeDao.delete(employee);
        }
        /*Make GET request to this servlet*/
        response.sendRedirect(request.getContextPath() + "/EmployeesServlet");
    }
    private WebContext getWebContext(HttpServletRequest request,
                                     HttpServletResponse response) {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(request, response);
        return new WebContext(webExchange);
    }
}
