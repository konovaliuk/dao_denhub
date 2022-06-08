package ua.kpi.pm_system.dao.interfaces;

import ua.kpi.pm_system.entities.ProjectRole;
import ua.kpi.pm_system.entities.Team;

import java.sql.SQLException;
import java.util.List;

public interface ITeamDao {
    List<Team> findProjectMembers(long projectId) throws SQLException;
    boolean checkRole(Team member) throws SQLException;
    Team addMember(long projectId, long userId, ProjectRole projectRole) throws SQLException;
    Team addMember(long projectId, long userId) throws SQLException;
    void changeRole(Team member, ProjectRole role) throws SQLException;
    void delete(Team member) throws SQLException;
}
