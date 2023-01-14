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
import ua.edu.znu.travelagencyweb.service.ClientDaoImpl;

import java.io.IOException;

/**
 * Adds a new client.
 */
@WebServlet("/ClientAddServlet")
public class ClientAddServlet extends HttpServlet {
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
        templateEngine.process("clientadd", context, response.getWriter());
    }
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {
        ClientDaoImpl clientDao = new ClientDaoImpl();
        Client client = new Client();
        String clientSurname = request.getParameter("clientSurname");
        client.setSurname(clientSurname);
        String clientName = request.getParameter("clientName");
        client.setName(clientName);
        int clientAge = Integer.parseInt(request.getParameter("clientAge"));
        client.setAge(clientAge);
        clientDao.create(client);
        client = clientDao.findBySurname(clientSurname);
        request.setAttribute("clientId", client.getId());
        request.setAttribute("action","clientAdd");
        request.getRequestDispatcher("/ClientsServlet")
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
