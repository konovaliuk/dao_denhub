package ua.kpi.pm_system.commands;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public interface ICommand {
    String execute(HttpServletRequest request) throws SQLException, NoSuchAlgorithmException;
}
