package com.example.health_calendar.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.health_calendar.daos.DateDao;
import com.example.health_calendar.entites.Date;

@Database(entities = {Date.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DateDao dayDao();
}
