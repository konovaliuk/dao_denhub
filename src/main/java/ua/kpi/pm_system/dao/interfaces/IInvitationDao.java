package ua.kpi.pm_system.dao.interfaces;

import ua.kpi.pm_system.entities.Invitation;
import ua.kpi.pm_system.entities.InvitationStatus;

import java.sql.SQLException;
import java.util.List;

public interface IInvitationDao {
    List<Invitation> findUserInvitations(long userId) throws SQLException;
    Invitation findInvitationById(long invitationId)throws SQLException;
    Invitation create(long idInvited, long idInviter, long idProject) throws SQLException;
    Invitation changeStatus(Invitation invitation, InvitationStatus newStatus) throws SQLException;
    void delete(Invitation invitation) throws SQLException;
}