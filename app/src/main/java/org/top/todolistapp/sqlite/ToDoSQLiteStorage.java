package org.top.todolistapp.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import org.top.todolistapp.model.ToDo;
import org.top.todolistapp.model.ToDoSelectParam;
import org.top.todolistapp.model.ToDoStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ToDoSQLiteStorage implements ToDoStorage {

    private static final String TABLE_NAME = "t_todos";
    private static final String COLUMN_ID = "f_id";
    private static final String COLUMN_TEXT = "f_text";
    private static final String COLUMN_PRIORITY = "f_priority";
    private static final String COLUMN_DONE = "f_done";
    private static final String COLUMN_DEADLINE = "f_deadline";

    private final DatabaseHelper dbHelper;

    public ToDoSQLiteStorage(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public void insert(ToDo toDo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TEXT, toDo.getText());
        values.put(COLUMN_PRIORITY, toDo.getPriority());
        values.put(COLUMN_DONE, toDo.getDone());
        values.put(COLUMN_DEADLINE,
                toDo.getDeadline() == null ? null : toDo.getDeadline().getTime());

        long newId = db.insertOrThrow(TABLE_NAME, null, values);
        toDo.setId((int) newId);
    }

    @Override
    public List<ToDo> selectAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try (Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_ID + " ASC")) {

            List<ToDo> result = new ArrayList<>(cursor.getCount());

            int idIdx = cursor.getColumnIndexOrThrow(COLUMN_ID);
            int textIdx = cursor.getColumnIndexOrThrow(COLUMN_TEXT);
            int priorityIdx = cursor.getColumnIndexOrThrow(COLUMN_PRIORITY);
            int doneIdx = cursor.getColumnIndexOrThrow(COLUMN_DONE);
            int deadlineIdx = cursor.getColumnIndexOrThrow(COLUMN_DEADLINE);

            while (cursor.moveToNext()) {
                ToDo todo = new ToDo();
                todo.setId(cursor.getInt(idIdx));
                todo.setText(cursor.getString(textIdx));
                todo.setPriority(cursor.getInt(priorityIdx));
                todo.setDone(cursor.getInt(doneIdx) != 0);
                if (cursor.isNull(deadlineIdx)) {
                    todo.setDeadline(null);
                } else {
                    todo.setDeadline(new Date(cursor.getLong(deadlineIdx)));
                }
                result.add(todo);
            }

            return result;
        }
    }

    @Override
    public void update(ToDo toDo) {
        Objects.requireNonNull(toDo, "toDo must not be null");
        Objects.requireNonNull(toDo.getId(), "toDo.id must not be null for update");

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TEXT, toDo.getText());
        values.put(COLUMN_PRIORITY, toDo.getPriority());
        values.put(COLUMN_DONE, toDo.getDone());
        values.put(COLUMN_DEADLINE,
                toDo.getDeadline() == null ? null : toDo.getDeadline().getTime());

        db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " = ?",
                new String[] { String.valueOf(toDo.getId()) });
    }

    @Override
    public void delete(Integer id) {
        Objects.requireNonNull(id, "id must not be null");

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(
                TABLE_NAME,
                COLUMN_ID + " = ?",
                new String[] { String.valueOf(id) });
    }
    @Override
    public void deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }
    @Override
    public List<ToDo> selectByParam(ToDoSelectParam param) {
        if (param == null) {
            return selectAll();
        }

        List<String> conditions = new ArrayList<>();
        List<String> args = new ArrayList<>();

        if (param.getText() != null) {
            conditions.add(COLUMN_TEXT + " LIKE ?");
            args.add("%" + param.getText() + "%");
        }
        if (param.getPriority() != null) {
            conditions.add(COLUMN_PRIORITY + " = ?");
            args.add(String.valueOf(param.getPriority()));
        }
        if (param.getDone() != null) {
            conditions.add(COLUMN_DONE + " = ?");
            args.add(param.getDone() ? "1" : "0");
        }
        if (param.getDeadlineFrom() != null) {
            conditions.add(COLUMN_DEADLINE + " >= ?");
            args.add(String.valueOf(param.getDeadlineFrom().getTime()));
        }
        if (param.getDeadlineTo() != null) {
            conditions.add(COLUMN_DEADLINE + " <= ?");
            args.add(String.valueOf(param.getDeadlineTo().getTime()));
        }

        String where = conditions.isEmpty()
                ? null
                : TextUtils.join(" AND ", conditions);
        String[] whereArgs = args.isEmpty()
                ? null
                : args.toArray(new String[0]);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try (Cursor cursor = db.query(
                TABLE_NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                COLUMN_ID + " ASC")) {

            return readAllFromCursor(cursor);
        }
    }

    private List<ToDo> readAllFromCursor(Cursor cursor) {
        List<ToDo> result = new ArrayList<>(cursor.getCount());

        int idIdx = cursor.getColumnIndexOrThrow(COLUMN_ID);
        int textIdx = cursor.getColumnIndexOrThrow(COLUMN_TEXT);
        int priorityIdx = cursor.getColumnIndexOrThrow(COLUMN_PRIORITY);
        int doneIdx = cursor.getColumnIndexOrThrow(COLUMN_DONE);
        int deadlineIdx = cursor.getColumnIndexOrThrow(COLUMN_DEADLINE);

        while (cursor.moveToNext()) {
            ToDo todo = new ToDo();
            todo.setId(cursor.getInt(idIdx));
            todo.setText(cursor.getString(textIdx));
            todo.setPriority(cursor.getInt(priorityIdx));
            todo.setDone(cursor.getInt(doneIdx) != 0);
            if (cursor.isNull(deadlineIdx)) {
                todo.setDeadline(null);
            } else {
                todo.setDeadline(new Date(cursor.getLong(deadlineIdx)));
            }
            result.add(todo);
        }

        return result;
    }
}
