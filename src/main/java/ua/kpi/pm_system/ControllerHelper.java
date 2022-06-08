package ua.kpi.pm_system;

import ua.kpi.pm_system.commands.ICommand;
import ua.kpi.pm_system.commands.SignUpCommand;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class ControllerHelper {
    private static ControllerHelper instance;
    private static HashMap<String, ICommand> commands = new HashMap<>();

    private ControllerHelper() {
        commands.put("/signup", new SignUpCommand());
    }

    public ICommand getCommand(HttpServletRequest req) {
        return commands.get(req.getRequestURI());
    }

    public static ControllerHelper getInstance() {
        if (instance == null) {
            instance = new ControllerHelper();
        }

        return instance;
    }
}
