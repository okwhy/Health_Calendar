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
    public List<DateSQL> GetByYear(int year){

        return dateDao.getByYear(year);
    }
    public List<DateSQL> GetByMonth(int year, byte month){

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
    public DateWithNotes getDate(int year, int mouth, int day){
        return dateDao.getDate(year, mouth, day);
    }
    public DateSQL getDateNoNotes(int year, int mouth, int day){
        return dateDao.getDateNoNotes(year, mouth, day);
    }
    public long insertDate(int year, int mouth, int day){
        return dateDao.insert(new DateSQL(year,mouth,day));
    }
    public long insertOrUpdateNote(Note note){
        Note note1=noteDao.getByCatAndFKId(note.getType(),note.getId_fkdate());
        if (note1!= null){
            note1.setValue(note.getValue());
            noteDao.update(note1);
            return note1.getId();
        }else{
            noteDao.insert(note);
            return note.getId();
        }

    }
    public List<Note>getAllNoteTest(){
        return noteDao.getAll();
    }
    public List<DateSQL>getAllDateTest(){
        return dateDao.getAll();
    }
    public DateSQL getDateById(long id){ return dateDao.getDateById(id);}
}
