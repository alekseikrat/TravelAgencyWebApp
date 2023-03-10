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
import ua.edu.znu.travelagencyweb.service.UserDaoImpl;
import javax.persistence.NoResultException;
import java.io.IOException;

/**
 * Servlet that processes user login.
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String action = request.getParameter("action");
        String messageText;
        String nextUrl;
        if (action.equals("logout")) {
            nextUrl = "login";
        } else {
            try {
                UserDaoImpl userDao = new UserDaoImpl();
                if (userDao.isAuthenticated(username, password)) {
                    messageText = "Hello, " + username + "!";
                    nextUrl = "home";
                    request.setAttribute("message", messageText);
                    request.getRequestDispatcher("HomeServlet")
                            .forward(request, response);
                } else {
                    messageText = "Authentication failed!";
                    nextUrl = "login";
                }
            } catch (NoResultException ex) {
                messageText = "No such username!";
                nextUrl = "login";
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
            context.setVariable("message", messageText);
        }
        templateEngine.process(nextUrl, context, response.getWriter());
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
