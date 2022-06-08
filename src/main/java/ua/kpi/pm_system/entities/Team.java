package ua.kpi.pm_system.entities;

import java.io.Serializable;
import java.util.Objects;

public class Team implements Serializable {

    private long projectId;
    private long userId;
    private ProjectRole projectRole;

    public Team() {
    }

    public Team(long projectId, long userId, ProjectRole projectRole) {
        this.projectId = projectId;
        this.userId = userId;
        this.projectRole = projectRole;
    }

    public long getProjectId() {
        return projectId;
    }

    public long getUserId() {
        return userId;
    }

    public ProjectRole getProjectRole() {
        return projectRole;
    }

    public void setProjectRole(ProjectRole projectRole) {
        this.projectRole = projectRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return projectId == team.projectId && userId == team.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, userId);
    }
}
