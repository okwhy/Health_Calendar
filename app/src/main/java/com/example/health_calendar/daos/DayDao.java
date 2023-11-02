package com.example.health_calendar.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.health_calendar.entites.Day;

import java.util.List;

@Dao
public interface DayDao {
    @Query("select * from day")
    List<Day> getAll();
}
