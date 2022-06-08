package ua.kpi.pm_system.entities;

import java.io.Serializable;
import java.util.Objects;

public class Invitation implements Serializable {
    private long id;
    private long idProject;
    private long idInvited;
    private long idInviter;
    private InvitationStatus invitationStatus;

    public Invitation() {
    }

    public Invitation(long id, long idInvited, long idInviter, long idProject, InvitationStatus invitationStatus) {
        this.id = id;
        this.idProject = idProject;
        this.idInvited = idInvited;
        this.idInviter = idInviter;
        this.invitationStatus = invitationStatus;
    }

    public long getId() {
        return id;
    }

    public long getIdProject() {
        return idProject;
    }

    public long getIdInvited() {
        return idInvited;
    }

    public long getIdInviter() {
        return idInviter;
    }

    public InvitationStatus getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(InvitationStatus invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invitation that = (Invitation) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
