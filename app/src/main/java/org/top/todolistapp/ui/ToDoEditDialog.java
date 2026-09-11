package org.top.todolistapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.top.todolistapp.R;
import org.top.todolistapp.model.ToDo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ToDoEditDialog extends DialogFragment {

    public interface OnSaveListener {
        void onSave(ToDo toDo);
    }

    private static final String ARG_ID = "id";
    private static final String ARG_TEXT = "text";
    private static final String ARG_PRIORITY = "priority";
    private static final String ARG_DONE = "done";
    private static final String ARG_DEADLINE = "deadline";

    private final SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private OnSaveListener saveListener;

    public void setOnSaveListener(OnSaveListener listener) {
        this.saveListener = listener;
    }

    public static ToDoEditDialog newInstance() {
        return new ToDoEditDialog();
    }

    public static ToDoEditDialog newInstance(ToDo toDo) {
        ToDoEditDialog dialog = new ToDoEditDialog();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, toDo.getId() == null ? 0 : toDo.getId());
        args.putString(ARG_TEXT, toDo.getText());
        args.putInt(ARG_PRIORITY, toDo.getPriority() == null ? 3 : toDo.getPriority());
        args.putBoolean(ARG_DONE, Boolean.TRUE.equals(toDo.getDone()));
        args.putLong(ARG_DEADLINE,
                toDo.getDeadline() == null ? -1 : toDo.getDeadline().getTime());
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_todo_edit, null);

        EditText textEdit = view.findViewById(R.id.textEdit);
        EditText priorityEdit = view.findViewById(R.id.priorityEdit);
        EditText deadlineEdit = view.findViewById(R.id.deadlineEdit);
        CheckBox doneEdit = view.findViewById(R.id.doneEdit);

        Bundle args = getArguments();
        final Integer editingId;
        if (args != null && args.containsKey(ARG_ID)) {
            editingId = args.getInt(ARG_ID);
            textEdit.setText(args.getString(ARG_TEXT));
            priorityEdit.setText(String.valueOf(args.getInt(ARG_PRIORITY)));
            doneEdit.setChecked(args.getBoolean(ARG_DONE));
            long deadlineMs = args.getLong(ARG_DEADLINE);
            if (deadlineMs > 0) {
                deadlineEdit.setText(dateFormat.format(new Date(deadlineMs)));
            }
        } else {
            editingId = null;
        }

        return new AlertDialog.Builder(requireContext())
                .setTitle(editingId == null ? "Новое дело" : "Редактировать дело")
                .setView(view)
                .setPositiveButton("Сохранить", (d, w) -> {
                    ToDo toDo = new ToDo();
                    toDo.setId(editingId);
                    toDo.setText(textEdit.getText().toString().trim());

                    try {
                        toDo.setPriority(Integer.parseInt(priorityEdit.getText().toString().trim()));
                    } catch (NumberFormatException e) {
                        toDo.setPriority(3);
                    }

                    toDo.setDone(doneEdit.isChecked());

                    String deadlineStr = deadlineEdit.getText().toString().trim();
                    if (!deadlineStr.isEmpty()) {
                        try {
                            toDo.setDeadline(dateFormat.parse(deadlineStr));
                        } catch (ParseException e) {
                            toDo.setDeadline(null);
                        }
                    }

                    if (saveListener != null) saveListener.onSave(toDo);
                })
                .setNegativeButton("Отмена", null)
                .create();
    }
}