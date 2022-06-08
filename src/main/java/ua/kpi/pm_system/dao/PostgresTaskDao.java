package ua.kpi.pm_system.dao;

import ua.kpi.pm_system.connection.PostgresConnector;
import ua.kpi.pm_system.dao.interfaces.ITaskDao;
import ua.kpi.pm_system.entities.Task;
import ua.kpi.pm_system.entities.TaskPriority;
import ua.kpi.pm_system.entities.TaskStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PostgresTaskDao implements ITaskDao {
    private final String tableName = "task.task";

    private final String ID = "id";
    private final String TITLE = "title";
    private final String INSTRUCTIONS = "instructions";
    private final String DATE_CREATED = "dateCreated";
    private final String DEADLINE = "deadline";
    private final String ID_PROJECT = "idProject";
    private final String ID_CREATOR = "idCreator";
    private final String ID_ASSIGNED = "idAssigned";
    private final String TASK_PRIORITY = "taskPriority";
    private final String TASK_STATUS = "taskStatus";

    private final TaskStatus DEFAULT_STATUS = TaskStatus.TODO;

    public PostgresTaskDao() {

    }

    private Task getTask(ResultSet rs) throws SQLException {
        long id = rs.getLong(ID);
        String title = rs.getString(TITLE);
        String instructions = rs.getString(INSTRUCTIONS);
        Date dateCreated = rs.getDate(DATE_CREATED);
        Date deadline = rs.getDate(DEADLINE);
        long idProject = rs.getLong(ID_PROJECT);
        long idCreator = rs.getLong(ID_CREATOR);
        long idAssigned = rs.getLong(ID_ASSIGNED);
        TaskPriority taskPriority = TaskPriority.valueOf(rs.getString(TASK_PRIORITY).toUpperCase());
        TaskStatus taskStatus = TaskStatus.valueOf(rs.getString(TASK_STATUS).toUpperCase());
        return new Task(id, title, instructions, dateCreated, deadline, idProject, idCreator, idAssigned, taskPriority, taskStatus);
    }
    @Override
    public Task findTaskById(long id) throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String query = "select * from " + tableName + " where " + ID + "=?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setLong(1, id);

        ResultSet rs = ps.executeQuery();

        Task task = null;
        if (rs.next()) {
            task = getTask(rs);
        }

        rs.close();
        ps.close();
        connection.close();

        return task;
    }

    @Override
    public List<Task> projectTasks(long idProject) throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String query = "select * from " + tableName + " where " + ID_PROJECT + "=?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setLong(1, idProject);

        ResultSet rs = ps.executeQuery();
        List<Task> projectTasks = new ArrayList<>();

        while (rs.next()) {
            projectTasks.add(getTask(rs));
        }

        rs.close();
        ps.close();
        connection.close();

        return projectTasks;
    }

    @Override
    public List<Task> userProjectTasks(long idProject, long idUser) throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String query = "select * from " + tableName + " where " + ID_PROJECT + "=? and (" + ID_ASSIGNED + "=? or " + ID_CREATOR + "=?)";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setLong(1, idProject);
        ps.setLong(2, idUser);
        ps.setLong(3, idUser);

        ResultSet rs = ps.executeQuery();
        List<Task> userProjectTasks = new ArrayList<>();

        while (rs.next()) {
            userProjectTasks.add(getTask(rs));
        }

        rs.close();
        ps.close();
        connection.close();

        return userProjectTasks;
    }

    @Override
    public Task create(String title, String instructions, Date dateCreated, Date deadline,
                       long idProject, long idCreator, long idAssigned, TaskPriority taskPriority) throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String fields = TITLE + "," + INSTRUCTIONS + "," + DATE_CREATED + "," + DEADLINE + "," + ID_PROJECT + "," +
                        ID_CREATOR + "," + ID_ASSIGNED + "," + TASK_PRIORITY;

        String command = "insert into " + tableName + "(" + fields + ")" + "values(?,?,?,?,?,?,?,?::taskPriority)";

        PreparedStatement ps = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, title);
        ps.setString(2, instructions);
        ps.setDate(3, dateCreated);
        ps.setDate(4, deadline);
        ps.setLong(5, idProject);
        ps.setLong(6, idCreator);
        ps.setLong(7, idAssigned);
        ps.setString(8, taskPriority.name().toLowerCase());

        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        Task task = null;
        if (rs.next()) {
            long newId = rs.getLong(1);
            task = new Task(newId, title, instructions, dateCreated, deadline, idProject, idCreator, idAssigned, taskPriority, DEFAULT_STATUS);
        }

        rs.close();
        ps.close();
        connection.close();

        return task;
    }

    private String getUpdateStatement(String updatedField, String definingField) {
        return "update " + tableName + " set "+ updatedField + "=? where " + definingField + "=?";
    }

    @Override
    public Task updateTitle(Task task, String newTitle) throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String command = getUpdateStatement(TITLE, ID);

        PreparedStatement ps = connection.prepareStatement(command);
        ps.setString(1, newTitle);
        ps.setLong(2, task.getId());
        ps.executeUpdate();
        ps.close();
        connection.close();

        task.setTitle(newTitle);
        return task;
    }

    @Override
    public Task updateInstructions(Task task, String newInstructions) throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String command = getUpdateStatement(INSTRUCTIONS, ID);

        PreparedStatement ps = connection.prepareStatement(command);
        ps.setString(1, newInstructions);
        ps.setLong(2, task.getId());
        ps.executeUpdate();
        ps.close();
        connection.close();

        task.setInstructions(newInstructions);
        return task;

    }

    @Override
    public Task updateDeadline(Task task, Date newDeadline) throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String command = getUpdateStatement(DEADLINE, ID);

        PreparedStatement ps = connection.prepareStatement(command);
        ps.setDate(1, newDeadline);
        ps.setLong(2, task.getId());
        ps.executeUpdate();
        ps.close();
        connection.close();

        task.setDeadline(newDeadline);
        return task;
    }

    @Override
    public Task updateAssigned(Task task, long newIdAssigned) throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String command = getUpdateStatement(ID_ASSIGNED, ID);

        PreparedStatement ps = connection.prepareStatement(command);
        ps.setLong(1, newIdAssigned);
        ps.setLong(2, task.getId());
        ps.executeUpdate();
        ps.close();
        connection.close();

        task.setIdAssigned(newIdAssigned);
        return task;
    }

    @Override
    public Task updateTaskPriority(Task task, TaskPriority newTaskPriority) throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String command = getUpdateStatement(TASK_PRIORITY, ID);
        PreparedStatement ps = connection.prepareStatement(command);
        ps.setString(1, newTaskPriority.name().toLowerCase() + "::taskPriority");
        ps.setLong(2, task.getId());
        ps.executeUpdate();
        ps.close();
        connection.close();

        task.setTaskPriority(newTaskPriority);
        return task;
    }

    @Override
    public Task updateTaskStatus(Task task, TaskStatus newTaskStatus) throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String command = getUpdateStatement(TASK_STATUS, ID);

        PreparedStatement ps = connection.prepareStatement(command);
        ps.setString(1, newTaskStatus.name().toLowerCase() + "::taskStatus");
        ps.setLong(2, task.getId());
        ps.executeUpdate();
        ps.close();
        connection.close();

        task.setTaskStatus(newTaskStatus);
        return task;
    }

    @Override
    public void delete(Task task) throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String command = "delete from " + tableName + " where " + ID + "=?";

        PreparedStatement ps = connection.prepareStatement(command);
        ps.setLong(1, task.getId());
        ps.executeUpdate();
        ps.close();
        connection.close();
    }
}
