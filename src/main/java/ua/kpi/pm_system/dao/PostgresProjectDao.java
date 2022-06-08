package ua.kpi.pm_system.dao;

import ua.kpi.pm_system.connection.PostgresConnector;
import ua.kpi.pm_system.dao.interfaces.IProjectDao;
import ua.kpi.pm_system.entities.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresProjectDao implements IProjectDao {
    private final String tableName = "project.project";
    private final String ID = "id";
    private final String TITLE = "title";
    private final String DESCRIPTION = "description";
    private final String DATE_START = "dateStart";
    private final String DATE_FINISH = "dateFinish";
    private final String SHOW_ALL = "showAll";
    private final String ALLOW_ALL = "allowAll";
    private final String ID_OWNER = "ownerId";

    private final boolean DEFAULT_SHOW_ALL = false;
    private final boolean DEFAULT_ALLOW_ALL = false;

    private final String SELECT = "select * from " + tableName + " where ";
    private final String INSERT = "insert into " + tableName + "(" + TITLE + "," + DESCRIPTION + "," + DATE_START
                                    + "," + DATE_FINISH + "," + ID_OWNER + ") " + "values(?,?,?,?,?)";
    private final String DELETE = "delete from " + tableName + " where ";

    public PostgresProjectDao(){

    }

    private Project getProject(ResultSet rs) throws SQLException{
        long id = rs.getLong(ID);
        String title = rs.getString(TITLE);
        String description = rs.getString(DESCRIPTION);
        Date dateStart = rs.getDate(DATE_START);
        Date dateFinish = rs.getDate(DATE_FINISH);
        boolean showAll = rs.getBoolean(SHOW_ALL);
        boolean allowAll = rs.getBoolean(ALLOW_ALL);
        long ownerId = rs.getLong(ID_OWNER);

        return new Project(id, title, description, dateStart, dateFinish, showAll, allowAll, ownerId);
    }

    @Override
    public Project findProjectById(long id)  throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String query = SELECT + ID + "=?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setLong(1, id);

        ResultSet rs = ps.executeQuery();
        Project project = null;
        if (rs.next()){
            project = getProject(rs);
        }

        rs.close();
        ps.close();
        connection.close();
        return project;
    }

    @Override
    public List<Project> findUserProjects(long idUser)  throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String query = SELECT + ID_OWNER + "=?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setLong(1, idUser);

        ResultSet rs = ps.executeQuery();
        List<Project> userProjects = new ArrayList<>();
        while(rs.next()) {
            userProjects.add(getProject(rs));
        }
        rs.close();
        ps.close();
        connection.close();
        return userProjects;
    }

    @Override
    public Project create(String title, String description, Date dateStart, Date dateFinish, long idOwner)  throws SQLException {
        Connection connection = PostgresConnector.getConnection();

        PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setDate(3, dateStart);
        ps.setDate(4, dateFinish);
        ps.setLong(5, idOwner);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        Project project = null;
        if (rs.next()) {
            long newId = rs.getLong(1);
            project = new Project(newId, title, description, dateStart, dateFinish, DEFAULT_SHOW_ALL, DEFAULT_ALLOW_ALL, idOwner);
        }

        rs.close();
        ps.close();
        connection.close();
        return project;
    }

    private String getUpdateStatement(String updatedField, String definingField) {
        return "update " + tableName + " set "+ updatedField + "=? where " + definingField + "=?";
    }

    @Override
    public Project updateTitle(Project project, String newTitle) throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String command = getUpdateStatement(TITLE, ID);

        PreparedStatement ps = connection.prepareStatement(command);
        ps.setString(1, newTitle);
        ps.setLong(2, project.getId());
        ps.executeUpdate();
        ps.close();
        connection.close();

        project.setTitle(newTitle);
        return project;
    }

    @Override
    public Project updateDescription(Project project, String newDescription) throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String command = getUpdateStatement(DESCRIPTION, ID);

        PreparedStatement ps = connection.prepareStatement(command);
        ps.setString(1, newDescription);
        ps.setLong(2, project.getId());
        ps.executeUpdate();
        ps.close();
        connection.close();

        project.setDescription(newDescription);
        return project;
    }

    @Override
    public Project updateDateStart(Project project, Date newDateStart) throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String command = getUpdateStatement(DATE_START, ID);

        PreparedStatement ps = connection.prepareStatement(command);
        ps.setDate(1, newDateStart);
        ps.setLong(2, project.getId());
        ps.executeUpdate();
        ps.close();
        connection.close();

        project.setDateStart(newDateStart);
        return project;
    }

    @Override
    public Project updateDateFinish(Project project, Date newDateFinish) throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String command = getUpdateStatement(DATE_FINISH, ID);

        PreparedStatement ps = connection.prepareStatement(command);
        ps.setDate(1, newDateFinish);
        ps.setLong(2, project.getId());
        ps.executeUpdate();
        ps.close();
        connection.close();

        project.setDateFinish(newDateFinish);
        return project;
    }

    @Override
    public Project updateShowAll(Project project, Boolean newShowAll) throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String command = getUpdateStatement(SHOW_ALL, ID);

        PreparedStatement ps = connection.prepareStatement(command);
        ps.setBoolean(1, newShowAll);
        ps.setLong(2, project.getId());
        ps.executeUpdate();
        ps.close();
        connection.close();

        project.setShowAll(newShowAll);
        return project;
    }

    @Override
    public Project updateAllowAll(Project project, Boolean newAllowAll) throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String command = getUpdateStatement(ALLOW_ALL, ID);

        PreparedStatement ps = connection.prepareStatement(command);
        ps.setBoolean(1, newAllowAll);
        ps.setLong(2, project.getId());
        ps.executeUpdate();
        ps.close();
        connection.close();

        project.setAllowAll(newAllowAll);
        return project;
    }

    @Override
    public Project updateIdOwner(Project project, long newIdOwner) throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String command = getUpdateStatement(ID_OWNER, ID);

        PreparedStatement ps = connection.prepareStatement(command);
        ps.setLong(1, newIdOwner);
        ps.setLong(2, project.getId());
        ps.executeUpdate();
        ps.close();
        connection.close();

        project.setOwnerId(newIdOwner);
        return project;
    }

    @Override
    public void delete(Project project)  throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String command = DELETE + ID + "=?";
        PreparedStatement ps = connection.prepareStatement(command);
        ps.setLong(1, project.getId());
        ps.executeUpdate();
        ps.close();
        connection.close();
    }
}
