package org.top.todolistapp.stub;

import org.top.todolistapp.model.ToDo;
import org.top.todolistapp.model.ToDoSelectParam;
import org.top.todolistapp.model.ToDoStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToDoStorageStub implements ToDoStorage {
    private final List<ToDo> data = new ArrayList<>(Helper.generateRandomToDos(3));

    @Override
    public List<ToDo> selectAll() {
        return data;
    }

    @Override
    public void insert(ToDo toDo) {
        data.add(toDo);
    }

    @Override
    public void update(ToDo toDo) {
        throw new UnsupportedOperationException("not implemented!");
    }

    @Override
    public void delete(Integer id) {
        data.removeIf(toDo -> Objects.equals(toDo.getId(), id));
    }

    @Override
    public void deleteAll() {
        data.clear();
    }

    @Override
    public List<ToDo> selectByParam(ToDoSelectParam param) {
        throw new UnsupportedOperationException("not implemented!");
    }
}
