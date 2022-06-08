package ua.kpi.pm_system.dao.interfaces;

import ua.kpi.pm_system.entities.Project;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface IProjectDao {
    Project findProjectById(long id) throws SQLException;
    List<Project> findUserProjects(long userId) throws SQLException;
    Project create(String title, String description, Date dateStart, Date dateFinish, long ownerId) throws SQLException;
    Project updateTitle(Project project, String newTitle) throws SQLException;
    Project updateDescription(Project project, String newDescription) throws SQLException;
    Project updateDateStart(Project project, Date newDateStart) throws SQLException;
    Project updateDateFinish(Project project, Date newDateFinish) throws SQLException;
    Project updateShowAll(Project project, Boolean newShowAll) throws SQLException;
    Project updateAllowAll(Project project, Boolean newAllowAll) throws SQLException;
    Project updateIdOwner(Project project, long newIdOwner) throws SQLException;
    void delete(Project project) throws SQLException;
}
