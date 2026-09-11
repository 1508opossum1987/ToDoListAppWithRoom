package org.top.todolistapp.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = { ToDoEntity.class }, version = 1, exportSchema = false)
@TypeConverters({ DateConverter.class })
public abstract class ToDoRoomDatabase extends RoomDatabase {
    public abstract ToDoDao toDoDao();
}