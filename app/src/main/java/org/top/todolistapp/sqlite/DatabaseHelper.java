package org.top.todolistapp.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// DatabaseHelper - класс, обеспечивающий открытие соединения с БД и
// существование актуальной схемы schema при ее создании и обновлении
public class DatabaseHelper extends SQLiteOpenHelper {

    // параметры БД
    private static final String DB_NAME = "todos.db";
    private static final int SCHEMA_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // инициализация БД если она отсутствует на устройстве
        db.execSQL(CREATE_TODO_TABLE);
        Log.d("DatabaseHelper", "onCreate ok");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TODO_TABLE);    // сначала удалить старую БД
        db.execSQL(CREATE_TODO_TABLE);  // создать новую по акутальной схеме
        Log.d("DatabaseHelper", "onUpgrade ok");
    }

    // SQL-запросы для работы со схемой БД: таблица t_todos: f_id, f_text, f_priority, f_done, f_deadline
    private static final String CREATE_TODO_TABLE = "CREATE TABLE IF NOT EXISTS t_todos (" +
            "f_id INTEGER PRIMARY KEY, " +
            "f_text TEXT NOT NULL, " +
            "f_priority INTEGER, " +
            "f_done BOOLEAN, " +
            "f_deadline INTEGER)";
    private static final String DROP_TODO_TABLE = "DROP TABLE t_todos;";
}
