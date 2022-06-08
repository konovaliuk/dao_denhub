package ua.kpi.pm_system.commands;

import ua.kpi.pm_system.services.ServiceFactory;
import ua.kpi.pm_system.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class SignUpCommand implements ICommand{
    private final UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest request) throws  SQLException, NoSuchAlgorithmException {
        if ("GET".equals(request.getMethod())) {
            return "/signUp.jsp";
        }
        else if ("POST".equals(request.getMethod())) {
            {
                if (userService.signUpUser(request.getParameter("email"), request.getParameter("username"), request.getParameter("password"),
                        request.getParameter("firstName"), request.getParameter("lastName"), request.getParameter("bio"))) {
                    return "/signIn.jsp";
                }
            }
        }

        return  "/index.jsp";
    }
}
