package ua.kpi.pm_system.services;

public class ServiceFactory {
    private static final UserService userService = new UserService();

    public static UserService getUserService() {
        return userService;
    }
}
