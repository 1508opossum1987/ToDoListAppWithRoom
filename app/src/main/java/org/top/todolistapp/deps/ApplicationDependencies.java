package org.top.todolistapp.deps;

import android.content.Context;

import androidx.room.Room;

import org.top.todolistapp.model.ToDoStorage;
import org.top.todolistapp.room.ToDoRoomDatabase;
import org.top.todolistapp.room.ToDoRoomStorage;
import org.top.todolistapp.sqlite.DatabaseHelper;
import org.top.todolistapp.sqlite.ToDoSQLiteStorage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApplicationDependencies {

    private static Context applicationContext;

    public static void initApplicationContext(Context context) {
        if (applicationContext != null) {
            throw new IllegalStateException("application context already initialized");
        }
        applicationContext = context.getApplicationContext();
    }

    private static DatabaseHelper databaseHelper;

    public static DatabaseHelper databaseHelper() {
        if (applicationContext == null) {
            throw new IllegalStateException("application context must be initialized");
        }
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(applicationContext);
        }
        return databaseHelper;
    }

    private static ToDoRoomDatabase roomDatabase;

    public static ToDoRoomDatabase roomDatabase() {
        if (applicationContext == null) {
            throw new IllegalStateException("application context must be initialized");
        }
        if (roomDatabase == null) {
            roomDatabase = Room.databaseBuilder(
                            applicationContext,
                            ToDoRoomDatabase.class,
                            "todos_room.db")
                    .build();
        }
        return roomDatabase;
    }

    private static ToDoStorage toDoStorage;

    public static ToDoStorage toDoStorage() {
        if (toDoStorage == null) {
            // SQLite-вариант
            // toDoStorage = new ToDoSQLiteStorage(databaseHelper());
            // Room-вариант:
            toDoStorage = new ToDoRoomStorage(roomDatabase().toDoDao());
        }
        return toDoStorage;
    }

    private static final ExecutorService ioExecutor = Executors.newSingleThreadExecutor();

    public static ExecutorService ioExecutor() {
        return ioExecutor;
    }
}