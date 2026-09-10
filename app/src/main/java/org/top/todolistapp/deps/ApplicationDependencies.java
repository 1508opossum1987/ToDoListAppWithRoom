package org.top.todolistapp.deps;

import android.content.Context;

import org.top.todolistapp.model.ToDoStorage;
import org.top.todolistapp.sqlite.DatabaseHelper;
import org.top.todolistapp.sqlite.ToDoSQLiteStorage;
import org.top.todolistapp.stub.ToDoStorageStub;

// ApplicationDependencies - класс для "скрещивания" зависимостей приложения
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

    private static ToDoStorage toDoStorage;

    public static ToDoStorage toDoStorage() {
        if (toDoStorage == null) {
            toDoStorage = new ToDoSQLiteStorage(databaseHelper());
        }
        return toDoStorage;
    }
}
