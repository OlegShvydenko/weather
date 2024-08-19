package controller.authentication;

import controller.BaseServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserAuthenticationService;
import util.exception.ValidateException;


import java.io.IOException;

@WebServlet("/register")
public class RegistrationServlet extends BaseServlet {
    private final UserAuthenticationService userAuthenticationService;

    public RegistrationServlet(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    public RegistrationServlet() {
        this.userAuthenticationService = new UserAuthenticationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.processTemplate("register", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            userAuthenticationService.registerUser(req);
        } catch (ValidateException e) {
            req.setAttribute("message", e.getMessage());
            super.processTemplate("register", req, resp);
        }
        resp.sendRedirect("login");
    }
}
