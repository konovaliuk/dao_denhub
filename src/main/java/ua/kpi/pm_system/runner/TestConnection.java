package ua.kpi.pm_system.runner;

import ua.kpi.pm_system.dao.*;
import ua.kpi.pm_system.dao.interfaces.*;
import ua.kpi.pm_system.entities.*;
import java.sql.*;
import java.util.List;

public class TestConnection {
    public static void main(String[] args) throws SQLException {
        IUserDao uDao = DaoFactory.getUserDao();

        User user = uDao.create("1515@gmail.com", "1515", "1111", "John", "Snow", "Hello");
        System.out.println("Id: " + user.getId() + ", username: " + user.getUsername() + ", first name: " + user.getFirstName());

        user = uDao.updateFirstName(user, "James");
        User user2 = uDao.findByUsername(user.getUsername());
        System.out.println("Id: " + user2.getId() + ", username: " + user2.getUsername() + ", first name: " + user2.getFirstName());

        uDao.delete(user);

        User getUser = uDao.findById(user.getId());
        System.out.println(getUser);

        User user3 = uDao.create("1516@gmail.com", "orcusbee", "1111", "Harry", "Potter", "");
        User user4 = uDao.create("1517@gmail.com", "puredeer", "1111", "Winnie", "Pooh", "");

        IProjectDao pDao = DaoFactory.getProjectDao();
        Project project1 = pDao.create("project1", "smth", Date.valueOf("2022-02-01"), Date.valueOf("2022-06-31"), user3.getId());
        Project project2 = pDao.create("project2", "", Date.valueOf("2022-06-01"), Date.valueOf("2022-06-31"), user3.getId());

        System.out.println("Project:");
        List<Project> harryProjects = pDao.findUserProjects(user3.getId());
        for (Project project : harryProjects) {
            System.out.println("Id: " + project.getId() + ", title: " + project.getTitle() + ", owner id: " + project.getOwnerId());
        }

        IInvitationDao iDao = DaoFactory.getInvitationDao();
        Invitation invitation1 = iDao.create(user4.getId(), user3.getId(), project1.getId());
        Invitation invitation2 = iDao.create(user4.getId(), user3.getId(), project2.getId());

        System.out.println("Invitations:");
        List<Invitation> winnieInvitations = iDao.findUserInvitations(user4.getId());
        for (Invitation invitation : winnieInvitations) {
            System.out.println("id: " + invitation.getId() + ", invited user id: " + invitation.getIdInvited()
                                + ", invitation status: " + invitation.getInvitationStatus().name());
        }

        System.out.println("Updated invitations:");
        invitation1 = iDao.changeStatus(invitation1, InvitationStatus.CONFIRMED);
        invitation2 = iDao.changeStatus(invitation2, InvitationStatus.REJECTED);
        System.out.println("id: " + invitation1.getId() + ", invited user id: " + invitation1.getIdInvited()
                + ", invitation status: " + invitation1.getInvitationStatus().name());
        System.out.println("id: " + invitation2.getId() + ", invited user id: " + invitation2.getIdInvited()
                + ", invitation status: " + invitation2.getInvitationStatus().name());

        ITeamDao postgresTeamDao = DaoFactory.getTeamDao();

        Team member1 = postgresTeamDao.addMember(project1.getId(), user3.getId(), ProjectRole.MANAGER);
        Team member2 = postgresTeamDao.addMember(project1.getId(), user4.getId());
        Team member3 = postgresTeamDao.addMember(project2.getId(), user3.getId(), ProjectRole.MANAGER);

        System.out.println("Permissions:");
        System.out.println("Harry permission: " + postgresTeamDao.checkRole(member1));
        System.out.println("Winnie permission: " + postgresTeamDao.checkRole(member2));


        System.out.println("Project1 team:");
        List<Team> project1Team = postgresTeamDao.findProjectMembers(project1.getId());
        for (Team member : project1Team) {
            System.out.println("Project id: " + member.getProjectId() + ", user id: " + member.getUserId()
                    + ", project role: " + member.getProjectRole().name());
        }

        ITaskDao taskDao = DaoFactory.getTaskDao();
        Task task1 = taskDao.create("task1", "...", Date.valueOf("2022-02-01"), Date.valueOf("2022-03-01"),
                project1.getId(), user3.getId(), user3.getId(), TaskPriority.HIGH);
        Task task2 = taskDao.create("task2", "...", Date.valueOf("2022-03-01"), Date.valueOf("2022-04-01"),
                project1.getId(), user3.getId(), user4.getId(), TaskPriority.MEDIUM);
        Task task3 = taskDao.create("task3", "...", Date.valueOf("2022-04-01"), Date.valueOf("2022-05-01"),
                project1.getId(), user4.getId(), user4.getId(), TaskPriority.LOW);

        System.out.println("Project tasks:");
        List<Task> projectTasks = taskDao.projectTasks(project1.getId());
        for (Task task : projectTasks) {
            System.out.println("Task id: " + task.getId() + ", title: " + task.getTitle() + ", project id: " + task.getIdProject()
            + ", assigned user id: " + task.getIdAssigned());
        }

        System.out.println("User Project tasks:");
        List<Task> userProjectTasks = taskDao.userProjectTasks(project1.getId(), user4.getId());
        for (Task task : userProjectTasks) {
            System.out.println("Task id: " + task.getId() + ", title: " + task.getTitle() + ", project id: " + task.getIdProject()
                    + ", assigned user id: " + task.getIdAssigned());
        }

        task2 = taskDao.updateAssigned(task2, user3.getId());
        System.out.println("Updated:");
        List<Task> userProjectTasks1 = taskDao.userProjectTasks(project1.getId(), user4.getId());
        for (Task task : userProjectTasks1) {
            System.out.println("Task id: " + task.getId() + ", title: " + task.getTitle() + ", project id: " + task.getIdProject()
                    + ", assigned user id: " + task.getIdAssigned());
        }
    }
}

