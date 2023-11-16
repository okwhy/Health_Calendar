package com.example.health_calendar.services;

import android.content.Context;

import androidx.room.Room;

import com.example.health_calendar.daos.DateDao;
import com.example.health_calendar.daos.NoteDao;
import com.example.health_calendar.database.AppDatabase;

public class DataService {
    private final DateDao dayDao;
    private final NoteDao noteDao;
    private static DataService dataService;

    public static DataService initial(Context context){
        if(dataService==null) dataService = new DataService(context);
        return dataService;
    }
    private DataService(Context context){
        AppDatabase appDatabase = Room.databaseBuilder(context,
                        AppDatabase.class, "myBase")
                .fallbackToDestructiveMigration()
                .build();
        dayDao = appDatabase.dayDao();
        noteDao = appDatabase.noteDao();
    }
}
