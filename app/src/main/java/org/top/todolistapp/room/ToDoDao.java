package org.top.todolistapp.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ToDoDao {

    @Query("SELECT * FROM t_todos ORDER BY f_id ASC")
    List<ToDoEntity> selectAll();

    @Insert
    long insert(ToDoEntity entity);

    @Update
    void update(ToDoEntity entity);

    @Query("DELETE FROM t_todos WHERE f_id = :id")
    void deleteById(Integer id);

    @Query("DELETE FROM t_todos")
    void deleteAll();

    @Query("SELECT * FROM t_todos WHERE " +
            "(:text IS NULL OR f_text LIKE :text) AND " +
            "(:priority IS NULL OR f_priority = :priority) AND " +
            "(:done IS NULL OR f_done = :done) AND " +
            "(:from IS NULL OR f_deadline >= :from) AND " +
            "(:to IS NULL OR f_deadline <= :to) " +
            "ORDER BY f_id ASC")
    List<ToDoEntity> selectByParam(String text, Integer priority,
                                   Boolean done, Long from, Long to);
}