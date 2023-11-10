package com.example.health_calendar.daos;

import androidx.room.Dao;
import androidx.room.Query;


import com.example.health_calendar.entites.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("select * from note")
    List<Note> getAll();
    @Query("select * from note where note.type = :type")
    List<Note> getByCat(String type);
    @Query("select * from note where note.type = :type and note.value=:value")
    List<Note> getByCat(String type,double value);
}
