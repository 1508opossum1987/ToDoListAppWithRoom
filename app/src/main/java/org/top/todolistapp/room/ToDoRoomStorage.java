package org.top.todolistapp.room;

import org.top.todolistapp.model.ToDo;
import org.top.todolistapp.model.ToDoSelectParam;
import org.top.todolistapp.model.ToDoStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ToDoRoomStorage implements ToDoStorage {

    private final ToDoDao dao;

    public ToDoRoomStorage(ToDoDao dao) {
        this.dao = dao;
    }

    @Override
    public List<ToDo> selectAll() {
        return toModelList(dao.selectAll());
    }

    @Override
    public void insert(ToDo toDo) {
        Objects.requireNonNull(toDo, "toDo must not be null");
        long newId = dao.insert(toEntity(toDo));
        toDo.setId((int) newId);
    }

    @Override
    public void update(ToDo toDo) {
        Objects.requireNonNull(toDo, "toDo must not be null");
        Objects.requireNonNull(toDo.getId(), "toDo.id must not be null for update");
        dao.update(toEntity(toDo));
    }

    @Override
    public void delete(Integer id) {
        Objects.requireNonNull(id, "id must not be null");
        dao.deleteById(id);
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
    }

    @Override
    public List<ToDo> selectByParam(ToDoSelectParam param) {
        if (param == null) return selectAll();

        String text = param.getText() == null ? null : "%" + param.getText() + "%";
        Integer priority = param.getPriority();
        Boolean done = param.getDone();
        Long from = param.getDeadlineFrom() == null ? null : param.getDeadlineFrom().getTime();
        Long to = param.getDeadlineTo() == null ? null : param.getDeadlineTo().getTime();

        return toModelList(dao.selectByParam(text, priority, done, from, to));
    }

    private static ToDoEntity toEntity(ToDo toDo) {
        ToDoEntity e = new ToDoEntity();
        e.id = toDo.getId();
        e.text = toDo.getText();
        e.priority = toDo.getPriority();
        e.done = toDo.getDone();
        e.deadline = toDo.getDeadline() == null ? null : toDo.getDeadline().getTime();
        return e;
    }

    private static ToDo toModel(ToDoEntity e) {
        ToDo toDo = new ToDo();
        toDo.setId(e.id);
        toDo.setText(e.text);
        toDo.setPriority(e.priority);
        toDo.setDone(e.done);
        toDo.setDeadline(e.deadline == null ? null : new Date(e.deadline));
        return toDo;
    }

    private static List<ToDo> toModelList(List<ToDoEntity> entities) {
        List<ToDo> result = new ArrayList<>(entities.size());
        for (ToDoEntity e : entities) result.add(toModel(e));
        return result;
    }
}