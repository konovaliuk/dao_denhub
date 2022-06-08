package ua.kpi.pm_system.dao.interfaces;

import ua.kpi.pm_system.entities.Task;
import ua.kpi.pm_system.entities.TaskPriority;
import ua.kpi.pm_system.entities.TaskStatus;

import java.sql.Date;
import java.sql.SQLException;

import java.util.List;

public interface ITaskDao {
    Task findTaskById(long id) throws SQLException;
    List<Task> projectTasks(long idProject) throws SQLException;
    List<Task> userProjectTasks(long idProject, long idUser) throws SQLException;
    Task create(String title, String instructions, Date dateCreated, Date deadline,
                long idProject, long idCreator, long idAssigned, TaskPriority taskPriority) throws SQLException;
    Task updateTitle(Task task, String newTitle) throws SQLException;
    Task updateInstructions(Task task, String newInstructions) throws SQLException;
    Task updateDeadline(Task task, Date newDeadline) throws SQLException;
    Task updateAssigned(Task task, long newAssignedId) throws SQLException;
    Task updateTaskPriority(Task task, TaskPriority newTaskPriority) throws SQLException;
    Task updateTaskStatus(Task task, TaskStatus newTaskStatus)  throws SQLException;
    void delete(Task task) throws SQLException;
}
