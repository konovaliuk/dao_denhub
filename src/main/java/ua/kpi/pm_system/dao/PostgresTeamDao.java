package ua.kpi.pm_system.dao;

import ua.kpi.pm_system.connection.PostgresConnector;
import ua.kpi.pm_system.dao.interfaces.ITeamDao;
import ua.kpi.pm_system.entities.ProjectRole;
import ua.kpi.pm_system.entities.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class PostgresTeamDao implements ITeamDao {
    private final String tableName = "team.team";
    private final String ID_PROJECT = "idProject";
    private final String ID_USER = "idUser";
    private final String PROJECT_ROLE = "projectRole";

    private final ProjectRole DEFAULT_ROLE = ProjectRole.EMPLOYEE;

    public PostgresTeamDao(){

    }

    private Team getMember(ResultSet rs) throws SQLException {
        long idUser = rs.getLong(ID_USER);
        long idProject = rs.getLong(ID_PROJECT);
        ProjectRole role = ProjectRole.valueOf(rs.getString(PROJECT_ROLE).toUpperCase());

        return new Team(idProject, idUser, role);
    }

    @Override
    public List<Team> findProjectMembers(long idProject) throws SQLException {
        Connection connection = PostgresConnector.getConnection();

        String query = "select * from " + tableName + " where " + ID_PROJECT + "=?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setLong(1, idProject);

        ResultSet rs = ps.executeQuery();
        List<Team> projectMembers = new ArrayList<>();
        while (rs.next()) {
            projectMembers.add(getMember(rs));

        }
        rs.close();
        ps.close();
        connection.close();

        return projectMembers;
    }

    @Override
    public boolean checkRole(Team member) throws SQLException {
        Connection connection = PostgresConnector.getConnection();

        String query = "select " + PROJECT_ROLE + " from " + tableName + " where " + ID_PROJECT + "=? and " + ID_USER + "=?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setLong(1, member.getProjectId());
        ps.setLong(2, member.getUserId());

        ResultSet rs = ps.executeQuery();
        ProjectRole role = null;
        if (rs.next()){
            role = ProjectRole.valueOf(rs.getString("projectRole").toUpperCase());
        }
        rs.close();
        ps.close();
        connection.close();

        return role == ProjectRole.MANAGER;
    }

    @Override
    public Team addMember(long idProject, long idUser, ProjectRole projectRole) throws SQLException {
        Connection connection = PostgresConnector.getConnection();

        String command = "insert into " + tableName + "(" + ID_PROJECT + "," + ID_USER + "," + PROJECT_ROLE + ") values(?,?,?::projectRole)";

        PreparedStatement ps = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
        ps.setLong(1, idProject);
        ps.setLong(2, idUser);
        ps.setString(3, Objects.requireNonNullElse(projectRole, DEFAULT_ROLE).name().toLowerCase());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        Team team = null;
        if (rs.next()) {
            long newId = rs.getLong(1);
            team = new Team(idProject, idUser, Objects.requireNonNullElse(projectRole, DEFAULT_ROLE));
        }
        rs.close();
        ps.close();
        connection.close();

        return team;
    }

    @Override
    public Team addMember(long idProject, long idUser) throws SQLException {
        return addMember(idProject, idUser, null);
    }

    @Override
    public void changeRole(Team member, ProjectRole newRole) throws SQLException {
        Connection connection = PostgresConnector.getConnection();

        String command = "update " + tableName + " set " + PROJECT_ROLE + "=?::projectRole where " + ID_PROJECT + "=? and " + ID_USER + "=?";

        PreparedStatement ps = connection.prepareStatement(command);
        ps.setString(1, newRole.name().toLowerCase());
        ps.setLong(2, member.getProjectId());
        ps.setLong(3, member.getUserId());
        ps.executeUpdate();
        ps.close();
        connection.close();
    }

    @Override
    public void delete(Team member) throws SQLException {
        Connection connection = PostgresConnector.getConnection();

        String command = "delete from " + tableName + " where " + ID_PROJECT + "=? and " + ID_USER + "=?";

        PreparedStatement ps = connection.prepareStatement(command);
        ps.setLong(1, member.getProjectId());
        ps.setLong(2, member.getUserId());
        ps.executeUpdate();
        ps.close();
        connection.close();
    }
}
