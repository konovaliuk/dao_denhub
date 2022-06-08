package ua.kpi.pm_system.entities;

import java.io.Serializable;

import java.sql.Date;
import java.util.Objects;

public class Task implements Serializable {
    private long id;
    private String title;
    private String instructions;
    private Date dateCreated;
    private Date deadline;
    private long idProject;
    private long idCreator;
    private long idAssigned;
    private TaskPriority taskPriority;
    private TaskStatus taskStatus;

    public Task() {
    }

    public Task(long id, String title, String instructions, Date dateCreated, Date deadline, long idProject,
                long idCreator, long idAssigned, TaskPriority taskPriority, TaskStatus taskStatus) {
        this.id = id;
        this.title = title;
        this.instructions = instructions;
        this.dateCreated = dateCreated;
        this.deadline = deadline;
        this.idProject = idProject;
        this.idCreator = idCreator;
        this.idAssigned = idAssigned;
        this.taskPriority = taskPriority;
        this.taskStatus = taskStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public long getIdProject() {
        return idProject;
    }

    public long getIdCreator() {
        return idCreator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public long getIdAssigned() {
        return idAssigned;
    }

    public void setIdAssigned(long idAssigned) {
        this.idAssigned = idAssigned;
    }

    public TaskPriority getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
