package ua.kpi.pm_system.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class Project implements Serializable {
    private long id;
    private String title;
    private String description;
    private Date dateStart;
    private Date dateFinish;
    private boolean showAll;
    private boolean allowAll;
    private long ownerId;

    public Project() {
    }

    public Project(long id, String title, String description, Date dateStart, Date dateFinish, boolean showAll,
                   boolean allowAll, long ownerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.showAll = showAll;
        this.allowAll = allowAll;
        this.ownerId = ownerId;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(Date dateFinish) {
        this.dateFinish = dateFinish;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public boolean isShowAll() {
        return showAll;
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }

    public boolean isAllowAll() {
        return allowAll;
    }

    public void setAllowAll(boolean allowAll) {
        this.allowAll = allowAll;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id == project.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
