package controller.authentication;

import controller.BaseServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserAuthenticationService;

import java.io.IOException;


@WebServlet("/logout")
public class LogoutServlet extends BaseServlet {
    private final UserAuthenticationService userAuthenticationService;

    public LogoutServlet(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    public LogoutServlet() {
        this.userAuthenticationService = new UserAuthenticationService();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userAuthenticationService.logoutUser(req, resp);
        super.processTemplate("index", req, resp);
    }
}
