package org.top.todolistapp.stub;

import org.top.todolistapp.model.ToDo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Helper {

    public static List<ToDo> generateRandomToDos(int n) {
        List<ToDo> todos = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            todos.add(generateRandomToDo());
        }
        return todos;
    }

    public static ToDo generateRandomToDo() {
        ToDo todo = new ToDo();

        // Generate random ID (1-1000)
        todo.setId((int) (Math.random() * 1000) + 1);

        // Generate random text
        String[] texts = {"Buy groceries", "Do homework", "Call mom", "Walk dog",
                "Clean room", "Read book", "Go to gym", "Pay bills"};
        todo.setText(texts[(int) (Math.random() * texts.length)]);

        // Generate random priority (1-5)
        todo.setPriority((int) (Math.random() * 5) + 1);

        // Generate random done status
        todo.setDone(Math.random() < 0.5);

        // Generate random deadline (next 1-30 days)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, (int) (Math.random() * 30) + 1);
        todo.setDeadline(calendar.getTime());

        return todo;
    }
}