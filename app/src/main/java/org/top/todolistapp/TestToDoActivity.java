package org.top.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
        runOnIo(() -> {
            try {
                ToDoStorage storage = ApplicationDependencies.toDoStorage();
                List<ToDo> todos = storage.selectAll();

                runOnUiThread(() -> {
                    ArrayAdapter<ToDo> adapter = new ArrayAdapter<>(
                            this, android.R.layout.simple_list_item_1, todos);
                    ListView listView = findViewById(R.id.listView);
                    listView.setAdapter(adapter);
                    Toast.makeText(this, "selectAll OK", Toast.LENGTH_LONG).show();
                });
            } catch (Exception ex) {
                runOnUiThread(() -> Toast.makeText(this,
                        "error: " + ex.getMessage(), Toast.LENGTH_LONG).show());
            }
        });
    }

    public void onTestDeleteAllClick(View view) {
        runOnIo(() -> {
            try {
                ToDoStorage storage = ApplicationDependencies.toDoStorage();
                storage.deleteAll();
                runOnUiThread(() -> Toast.makeText(this,
                        "deleteAll OK", Toast.LENGTH_LONG).show());
            } catch (Exception ex) {
                runOnUiThread(() -> Toast.makeText(this,
                        "error: " + ex.getMessage(), Toast.LENGTH_LONG).show());
            }
        });
    }

    public void onTestInsertClick(View view) {
        runOnIo(() -> {
            try {
                ToDoStorage storage = ApplicationDependencies.toDoStorage();
                storage.insert(Helper.generateRandomToDo());
                runOnUiThread(() -> Toast.makeText(this,
                        "insert OK", Toast.LENGTH_LONG).show());
            } catch (Exception ex) {
                runOnUiThread(() -> Toast.makeText(this,
                        "error: " + ex.getMessage(), Toast.LENGTH_LONG).show());
            }
        });
    }

    public void onGoBackClick(View view) {
        if (getParent() == null || getParent().isDestroyed()) {
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }

    private void runOnIo(Runnable r) {
        ApplicationDependencies.ioExecutor().execute(r);
    }
}