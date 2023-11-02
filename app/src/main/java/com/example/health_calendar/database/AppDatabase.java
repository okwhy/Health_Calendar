package com.example.health_calendar.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.health_calendar.daos.DayDao;
import com.example.health_calendar.entites.Day;

@Database(entities = {Day.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DayDao dayDao();
}
