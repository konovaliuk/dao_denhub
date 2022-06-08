package ua.kpi.pm_system.dao;

import ua.kpi.pm_system.dao.interfaces.*;

import java.sql.Connection;

public class DaoFactory {
    private static final IUserDao userDao = new PostgresUserDao();
    private static final IProjectDao projectDao = new PostgresProjectDao();
    private static final ITeamDao teamDao = new PostgresTeamDao();
    private static final IInvitationDao invitationDao = new PostgresInvitationDao();
    private static final ITaskDao taskDao = new PostgresTaskDao();

    public static IUserDao getUserDao() {
        return userDao;
    }
    public static IProjectDao getProjectDao() {
        return projectDao;
    }
    public static IInvitationDao getInvitationDao() {
        return invitationDao;
    }
    public static ITeamDao getTeamDao() {
        return teamDao;
    }
    public static ITaskDao getTaskDao() {
        return taskDao;
    }
}
