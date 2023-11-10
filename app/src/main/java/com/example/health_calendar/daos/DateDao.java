package com.example.health_calendar.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.health_calendar.entites.Date;

import java.util.List;

@Dao
public interface DateDao {
    @Query("select * from date")
    List<Date> getAll();
}
