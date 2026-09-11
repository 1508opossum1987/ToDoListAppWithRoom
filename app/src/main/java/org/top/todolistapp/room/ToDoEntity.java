package org.top.todolistapp.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "t_todos")
public class ToDoEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "f_id")
    public Integer id;

    @ColumnInfo(name = "f_text")
    public String text;

    @ColumnInfo(name = "f_priority")
    public Integer priority;

    @ColumnInfo(name = "f_done")
    public Boolean done;

    @ColumnInfo(name = "f_deadline")
    public Long deadline;
}