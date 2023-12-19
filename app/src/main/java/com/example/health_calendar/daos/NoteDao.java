package com.example.health_calendar.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.health_calendar.entites.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("select * from note")
    List<Note> getAll();
    @Update
    void update(Note note);
    @Insert
    long insert(Note note);
    @Delete
    void delete(Note note);
    @Query("select * from note where note.type = :type")
    List<Note> getByCat(String type);
    @Query("select * from note where note.type = :type and note.value=:value")
    List<Note> getByCat(String type,double value);
    @Query("select * from note where note.type = :type and note.id_fkdate=:id")
    Note getByCatAndFKId(String type,long id);
}
