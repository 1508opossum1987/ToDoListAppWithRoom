package org.top.todolistapp.model;

import java.util.List;

// ToDoStorage - хранилище списка дел, определяет CRUD-операции работы с ToDo
public interface ToDoStorage {
    List<ToDo> selectAll();
    void insert(ToDo toDo);
    void update(ToDo toDo);
    void delete(Integer id);
    void deleteAll();
    List<ToDo> selectByParam(ToDoSelectParam param);
}
