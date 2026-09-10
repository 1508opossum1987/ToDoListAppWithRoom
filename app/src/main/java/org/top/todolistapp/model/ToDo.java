package org.top.todolistapp.model;

import androidx.annotation.NonNull;

import java.util.Date;

// ToDo - дело, которое надо сделано, элемент списка дел
public class ToDo {
    private Integer id;
    private String text;
    private Integer priority;
    private Boolean done;
    private Date deadline;

    public ToDo() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @NonNull
    @Override
    public String toString() {
        return id + " - " + text + " - " + deadline;
    }
}
