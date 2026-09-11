package org.top.todolistapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.top.todolistapp.R;
import org.top.todolistapp.model.ToDo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(ToDo toDo);
    }

    public interface OnDoneChangedListener {
        void onDoneChanged(ToDo toDo, boolean done);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(ToDo toDo);
    }

    private final List<ToDo> items = new ArrayList<>();
    private final SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    private OnItemClickListener itemClickListener;
    private OnDoneChangedListener doneChangedListener;
    private OnItemLongClickListener longClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setOnDoneChangedListener(OnDoneChangedListener listener) {
        this.doneChangedListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    public void setItems(List<ToDo> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        ToDo toDo = items.get(position);
        holder.bind(toDo);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ToDoViewHolder extends RecyclerView.ViewHolder {
        final CheckBox doneCheckBox;
        final TextView textTextView;
        final TextView priorityTextView;
        final TextView deadlineTextView;

        ToDoViewHolder(@NonNull View itemView) {
            super(itemView);
            doneCheckBox = itemView.findViewById(R.id.doneCheckBox);
            textTextView = itemView.findViewById(R.id.textTextView);
            priorityTextView = itemView.findViewById(R.id.priorityTextView);
            deadlineTextView = itemView.findViewById(R.id.deadlineTextView);
        }

        void bind(ToDo toDo) {
            textTextView.setText(toDo.getText());
            priorityTextView.setText("P: " + toDo.getPriority());
            deadlineTextView.setText(toDo.getDeadline() == null
                    ? "—"
                    : dateFormat.format(toDo.getDeadline()));

            // Перед setChecked — снять слушатель, чтобы не сработал при bind
            doneCheckBox.setOnCheckedChangeListener(null);
            doneCheckBox.setChecked(Boolean.TRUE.equals(toDo.getDone()));
            doneCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (doneChangedListener != null) {
                    doneChangedListener.onDoneChanged(toDo, isChecked);
                }
            });

            itemView.setOnClickListener(v -> {
                if (itemClickListener != null) itemClickListener.onItemClick(toDo);
            });
            itemView.setOnLongClickListener(v -> {
                if (longClickListener != null) {
                    longClickListener.onItemLongClick(toDo);
                    return true;
                }
                return false;
            });
        }
    }
}