package org.top.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.top.todolistapp.deps.ApplicationDependencies;
import org.top.todolistapp.model.ToDo;
import org.top.todolistapp.model.ToDoStorage;
import org.top.todolistapp.stub.Helper;

import java.util.List;

public class TestToDoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_to_do);
    }

    public void onTestSelectAllClick(View view) {
        try {
            ToDoStorage storage = ApplicationDependencies.toDoStorage();
            List<ToDo> todos = storage.selectAll();

            // введем список дел List<ToDo> на ListView
            ArrayAdapter<ToDo> todosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todos);
            ListView listView = findViewById(R.id.listView);
            listView.setAdapter(todosAdapter);

            Toast.makeText(this, "selectAll OK", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(this, "error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onTestDeleteAllClick(View view) {
        try {
            ToDoStorage storage = ApplicationDependencies.toDoStorage();
            storage.deleteAll();
            Toast.makeText(this, "deleteAll OK", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(this, "error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onTestInsertClick(View view) {
        try {
            ToDoStorage storage = ApplicationDependencies.toDoStorage();
            storage.insert(Helper.generateRandomToDo());
            Toast.makeText(this, "insert OK", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(this, "error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onGoBackClick(View view) {
        // finish(); // простой вариант, но если родительская активити была уничтожена (destoryed) то попадем в ОС
        if (getParent() == null || getParent().isDestroyed()) {
            // если родительская Acitivty была уничтожена то запустить другой ее экземпляр
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }
}