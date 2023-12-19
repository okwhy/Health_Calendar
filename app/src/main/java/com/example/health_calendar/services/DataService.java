package com.example.health_calendar.services;

import android.content.Context;

import com.example.health_calendar.daos.DateDao;
import com.example.health_calendar.daos.NoteDao;
import com.example.health_calendar.database.AppDatabase;
import com.example.health_calendar.entites.DateSQL;
import com.example.health_calendar.entites.DateWithNotes;
import com.example.health_calendar.entites.Note;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataService {
    private final DateDao dateDao;
    private final NoteDao noteDao;
    private static DataService dataService;
    private Set<String> NoteCategories = new HashSet<>(Arrays.asList(
                "HEIGHT",
                "WEIGHT",
                "PULSE",
                "PRESSURE",
               "APPETITE",
                "SLEEP",
                "HEALTH"
    )
    );


    public static DataService initial(Context context){
        if(dataService==null) dataService = new DataService(context);
        return dataService;
    }
    private DataService(Context context){
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        dateDao = appDatabase.dateDao();
        noteDao = appDatabase.noteDao();
    }
    public List<DateSQL> GetAllData(){
        return dateDao.getAll();
    }
    public List<DateSQL> GetByYear(byte year){

        return dateDao.getByYear(year);
    }
    public List<DateSQL> GetByMonth(byte year, byte month){

        return dateDao.getByMonth(year,month);
    }
    public List<Note> GetAll(){

        return noteDao.getAll();
    }

    public List<Note> GetByCat(String type){
        if (NoteCategories.contains(type))
            return noteDao.getByCat(type);

        return null;
    }

    public List<Note> GetByCat(String type,double value){

        if (NoteCategories.contains(type))
            return noteDao.getByCat(type,value);

        return null;
    }
    public DateWithNotes getDate(byte year, byte mouth, byte day){
        return dateDao.getDate(year, mouth, day);
    }

}
