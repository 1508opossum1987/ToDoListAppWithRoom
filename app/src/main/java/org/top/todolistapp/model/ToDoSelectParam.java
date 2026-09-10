package org.top.todolistapp.model;

import java.util.Date;

public class ToDoSelectParam {

    private String text;          // (LIKE '%...%')
    private Integer priority;     // ==
    private Boolean done;         // ==
    private Date deadlineFrom;    // >=
    private Date deadlineTo;      //  <=

    public ToDoSelectParam() {}

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

    public Date getDeadlineFrom() {
        return deadlineFrom;
    }

    public void setDeadlineFrom(Date deadlineFrom) {
        this.deadlineFrom = deadlineFrom;
    }

    public Date getDeadlineTo() {
        return deadlineTo;
    }

    public void setDeadlineTo(Date deadlineTo) {
        this.deadlineTo = deadlineTo;
    }
}
