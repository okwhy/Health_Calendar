package com.example.health_calendar.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.health_calendar.daos.DateDao;
import com.example.health_calendar.daos.NoteDao;
import com.example.health_calendar.entites.DateSQL;
import com.example.health_calendar.entites.Note;

@Database(entities = {DateSQL.class, Note.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DateDao dateDao();
    public abstract NoteDao noteDao();
    private final static String DB_NAME ="calender_db";
    private static AppDatabase instance;
    public static synchronized AppDatabase getInstance(Context context){
        if( instance == null){
            instance= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
