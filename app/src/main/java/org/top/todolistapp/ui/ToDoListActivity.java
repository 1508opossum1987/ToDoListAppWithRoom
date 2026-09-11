package org.top.todolistapp.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.top.todolistapp.R;
import org.top.todolistapp.deps.ApplicationDependencies;
import org.top.todolistapp.model.ToDo;
import org.top.todolistapp.model.ToDoSelectParam;
import org.top.todolistapp.model.ToDoStorage;

import java.util.List;

public class ToDoListActivity extends AppCompatActivity {

    private ToDoAdapter adapter;
    private ToDoStorage storage;
    private EditText searchEditText;
    private CheckBox onlyNotDoneCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        storage = ApplicationDependencies.toDoStorage();

        searchEditText = findViewById(R.id.searchEditText);
        onlyNotDoneCheckBox = findViewById(R.id.onlyNotDoneCheckBox);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton addFab = findViewById(R.id.addFab);

        adapter = new ToDoAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(this::onEdit);
        adapter.setOnItemLongClickListener(this::onDelete);
        adapter.setOnDoneChangedListener(this::onDoneChanged);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) { reload(); }
        });

        onlyNotDoneCheckBox.setOnCheckedChangeListener((v, c) -> reload());

        addFab.setOnClickListener(v -> {
            ToDoEditDialog dialog = ToDoEditDialog.newInstance();
            dialog.setOnSaveListener(toDo -> runOnIo(() -> {
                storage.insert(toDo);
                runOnUiThread(this::reload);
            }));
            dialog.show(getSupportFragmentManager(), "edit");
        });

        reload();
    }

    private void reload() {
        runOnIo(() -> {
            ToDoSelectParam param = new ToDoSelectParam();

            String text = searchEditText.getText().toString().trim();
            if (!text.isEmpty()) param.setText(text);

            if (onlyNotDoneCheckBox.isChecked()) param.setDone(false);

            List<ToDo> todos = storage.selectByParam(param);
            runOnUiThread(() -> adapter.setItems(todos));
        });
    }

    private void onEdit(ToDo toDo) {
        ToDoEditDialog dialog = ToDoEditDialog.newInstance(toDo);
        dialog.setOnSaveListener(updated -> runOnIo(() -> {
            storage.update(updated);
            runOnUiThread(this::reload);
        }));
        dialog.show(getSupportFragmentManager(), "edit");
    }

    private void onDelete(ToDo toDo) {
        new AlertDialog.Builder(this)
                .setTitle("Удалить дело?")
                .setMessage(toDo.getText())
                .setPositiveButton("Удалить", (d, w) -> runOnIo(() -> {
                    storage.delete(toDo.getId());
                    runOnUiThread(this::reload);
                }))
                .setNegativeButton("Отмена", null)
                .show();
    }

    private void onDoneChanged(ToDo toDo, boolean done) {
        toDo.setDone(done);
        runOnIo(() -> {
            storage.update(toDo);
            runOnUiThread(() -> Toast.makeText(this,
                    done ? "Выполнено" : "Снова активно", Toast.LENGTH_SHORT).show());
        });
    }

    private void runOnIo(Runnable r) {
        ApplicationDependencies.ioExecutor().execute(r);
    }
}