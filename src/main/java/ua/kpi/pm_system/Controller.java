package ua.kpi.pm_system;

import ua.kpi.pm_system.commands.ICommand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@WebServlet(name="Controller", urlPatterns = {"/signup"})
public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            processRequest(req, resp);
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try {
            processRequest(req, resp);
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, NoSuchAlgorithmException {
        ICommand command = ControllerHelper.getInstance().getCommand(req);
        String page = command.execute(req);
        req.getServletContext().getRequestDispatcher(page).forward(req, resp);
    }
}