package ua.kpi.pm_system.dao;

import ua.kpi.pm_system.connection.PostgresConnector;
import ua.kpi.pm_system.dao.interfaces.IInvitationDao;
import ua.kpi.pm_system.entities.Invitation;
import ua.kpi.pm_system.entities.InvitationStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PostgresInvitationDao implements IInvitationDao {
    private final String tableName = "invitation.invitation";
    private final String ID = "id";
    private final String ID_INVITED = "idInvited";
    private final String ID_INVITER = "idInviter";
    private final String ID_PROJECT = "idProject";
    private final String INVITATION_STATUS = "inv_status";

    private final InvitationStatus DEFAULT_STATUS = InvitationStatus.NOT_PROCEED;

    public PostgresInvitationDao() {
    }

    private Invitation getInvitation(ResultSet rs) throws SQLException {
        long idInvitation = rs.getLong("id");
        long idInvited = rs.getLong("idInvited");
        long idInviter = rs.getLong("idInviter");
        long idProject = rs.getLong("idProject");
        InvitationStatus status = InvitationStatus.valueOf(rs.getString("inv_status").toUpperCase());
        return new Invitation(idInvitation, idInvited, idInviter, idProject, status);
    }

    @Override
    public List<Invitation> findUserInvitations(long idUser) throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String query = "select * from " + tableName + " where " + ID_INVITED + "=? and " + INVITATION_STATUS + "='not_proceed'::invitationStatus";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setLong(1, idUser);

        ResultSet rs = ps.executeQuery();
        List<Invitation> userInvitations = new ArrayList<>();
        while(rs.next()) {
            userInvitations.add(getInvitation(rs));
        }
        rs.close();
        ps.close();
        connection.close();

        return userInvitations;
    }

    @Override
    public Invitation findInvitationById(long idInvitation) throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String query = "select * from " + tableName + " where " + ID + "=?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setLong(1, idInvitation);

        ResultSet rs = ps.executeQuery();
        Invitation invitation = null;
        if(rs.next()) {
            invitation = getInvitation(rs);
        }
        rs.close();
        ps.close();
        connection.close();
        return invitation;
    }

    @Override
    public Invitation create(long idInvited, long idInviter, long idProject) throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String fields = ID_INVITED + ',' + ID_INVITER + ',' + ID_PROJECT;
        String command = "insert into " + tableName + "(" + fields + ") values(?,?,?)";

        PreparedStatement ps = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
        ps.setLong(1, idInvited);
        ps.setLong(2, idInviter);
        ps.setLong(3, idProject);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        Invitation invitation = null;
        if (rs.next()) {
            long newId = rs.getLong(1);
            invitation = new Invitation(newId, idInvited, idInviter, idProject, DEFAULT_STATUS);
        }
        rs.close();
        ps.close();
        connection.close();

        return invitation;
    }

    @Override
    public Invitation changeStatus(Invitation invitation, InvitationStatus newStatus) throws SQLException{
        Connection connection = PostgresConnector.getConnection();
        String command = "update " + tableName + " set " + INVITATION_STATUS + "=?::invitationStatus where " + ID + "=?";

        PreparedStatement ps = connection.prepareStatement(command);
        ps.setString(1, newStatus.name().toLowerCase());
        ps.setLong(2, invitation.getId());
        ps.executeUpdate();
        ps.close();
        connection.close();
        invitation.setInvitationStatus(newStatus);
        return invitation;
    }

    @Override
    public void delete(Invitation invitation) throws SQLException{
        Connection connection = PostgresConnector.getConnection();
        String command = "delete from " + tableName + " where " + ID + "=?";
        PreparedStatement ps = connection.prepareStatement(command);
        ps.setLong(1, invitation.getId());
        ps.executeUpdate();
        ps.close();
        connection.close();
    }
}
