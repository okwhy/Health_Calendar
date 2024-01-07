package com.example.health_calendar.services;

import android.content.Context;

import com.example.health_calendar.daos.DateDao;
import com.example.health_calendar.daos.NoteDao;
import com.example.health_calendar.database.AppDatabase;
import com.example.health_calendar.dtos.PressureValue;
import com.example.health_calendar.entites.DateSQL;
import com.example.health_calendar.entites.DateWithNotes;
import com.example.health_calendar.entites.Note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataService {
    private final DateDao dateDao;
    private final NoteDao noteDao;
    private static DataService dataService;


    public Set<String> getNoteCategories() {
        return NoteCategories;
    }

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
    public List<DateWithNotes> getBetween(int byear, int ayear, int bmonth, int amonth, int bdate, int adate){
        return dateDao.getBetween(byear,ayear,bmonth,amonth,bdate,adate);
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
    public float getMediumByDateandtype(String type, int byear, int ayear,
                                        int bmonth, int amonth, int bdate, int adate){
        return dateDao.getAVGNotesByTypeBetweenDates(type, byear, ayear, bmonth, amonth, bdate, adate);
    }
    public List<String> getNotesByDateAndTypeS(String type, int byear, int ayear,
                                               int bmonth, int amonth, int bdate, int adate){
        return dateDao.getNotesByTypeBetweenDatesNoCast(type, byear, ayear, bmonth, amonth, bdate, adate);
    }
    public List<Float> getNotesByDateAndTypeF(String type, int byear, int ayear,
                                              int bmonth, int amonth, int bdate, int adate){
        return dateDao.getNotesByTypeBetweenDatesCast(type, byear, ayear, bmonth, amonth, bdate, adate);
    }
    public List<PressureValue>getPressureByDate(int byear, int ayear,
                                                int bmonth, int amonth, int bdate, int adate){
        List<String> pressureStrings=dateDao.getNotesByTypeBetweenDatesNoCast("PRESSURE", byear, ayear, bmonth, amonth, bdate, adate);
        List<PressureValue> pressureValues=new ArrayList<>();
        for(String s:pressureStrings){
            String[] vals=s.split("/");
            pressureValues.add(new PressureValue(Integer.parseInt(vals[0]),Integer.parseInt(vals[1])));
        }
        return pressureValues;
    }
    public String getMostCommonNote(String type, int byear, int ayear,
                                    int bmonth, int amonth, int bdate, int adate){
        return dateDao.getMostCommonNote(type, byear, ayear, bmonth, amonth, bdate, adate);
    }
}
