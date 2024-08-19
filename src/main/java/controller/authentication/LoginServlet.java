package controller.authentication;

import controller.BaseServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserAuthenticationService;
import util.exception.ValidateException;


import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends BaseServlet {
    private final UserAuthenticationService userAuthenticationService;

    public LoginServlet(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    public LoginServlet() {
        this.userAuthenticationService = new UserAuthenticationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.processTemplate("login", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (userAuthenticationService.verifyUserData(req)){
                userAuthenticationService.loginUser(req, resp);
            }
            super.processTemplate("index", req, resp);
        } catch (ValidateException e) {
            req.setAttribute("message", e.getMessage());
            super.processTemplate("login", req, resp);
        }

    }
}
