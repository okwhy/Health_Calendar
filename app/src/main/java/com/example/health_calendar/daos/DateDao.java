package com.example.health_calendar.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.health_calendar.entites.DateSQL;
import com.example.health_calendar.entites.DateWithNotes;

import java.util.List;

@Dao
public interface DateDao {
    @Query("select * from DateSQL")
    List<DateSQL> getAll();
    @Delete
    void delete(DateSQL... dates);
    @Insert
    long insert(DateSQL dateSQL);

    @Query("select * from DateSQL where DateSQL.year =:year order by month,day")
    List<DateSQL>getByYear(int year);
    @Query("select * from DateSQL where DateSQL.year =:year and DateSQL.month =:month order by day")
    List<DateSQL>getByMonth(int year, int month);
    @Query("SELECT * FROM DateSQL WHERE DateSQL.year BETWEEN :byear AND :ayear AND " +
            "DateSQL.month BETWEEN :bmonth and :amonth AND DateSQL.day BETWEEN" +
            ":bdate and :adate")
    List<DateWithNotes>getBetween(int byear, int ayear, int bmonth, int amonth, int bdate, int adate);

    @Query("select * from DateSQL where DateSQL.year =:year and DateSQL.month =:month and DateSQL.day =:day")
    DateWithNotes getDate(int year, int month, int day);
    @Query("select * from DateSQL where DateSQL.year =:year and DateSQL.month =:month and DateSQL.day =:day")
    DateSQL getDateNoNotes(int year, int month, int day);
    @Query("select * from DateSQL where DateSQL.id=:id")
    DateSQL getDateById(long id);
    @Query("select AVG(CAST(Note.value as float)) from DateSQL join Note on Note.id_fkdate=DateSQL.id " +
            "where Note.type=:type and DateSQL.year BETWEEN :byear AND :ayear AND " +
            "DateSQL.month BETWEEN :bmonth and :amonth AND DateSQL.day BETWEEN" +
            ":bdate and :adate")
    float getAVGNotesByTypeBetweenDates(String type, int byear, int ayear, int bmonth, int amonth, int bdate, int adate);
    @Query("select Note.value from DateSQL join Note on Note.id_fkdate=DateSQL.id " +
            "where Note.type=:type and DateSQL.year BETWEEN :byear AND :ayear AND " +
            "DateSQL.month BETWEEN :bmonth and :amonth AND DateSQL.day BETWEEN" +
            ":bdate and :adate")
    List<String> getNotesByTypeBetweenDatesNoCast(String type, int byear, int ayear, int bmonth, int amonth, int bdate, int adate);
    @Query("select CAST(Note.value as float) from DateSQL join Note on Note.id_fkdate=DateSQL.id " +
            "where Note.type=:type and DateSQL.year BETWEEN :byear AND :ayear AND " +
            "DateSQL.month BETWEEN :bmonth and :amonth AND DateSQL.day BETWEEN" +
            ":bdate and :adate")
    List<Float> getNotesByTypeBetweenDatesCast(String type, int byear, int ayear, int bmonth, int amonth, int bdate, int adate);
    @Query("select value  from DateSQL join Note on Note.id_fkdate=DateSQL.id " +
            "where Note.type=:type and DateSQL.year BETWEEN :byear AND :ayear AND " +
            "DateSQL.month BETWEEN :bmonth and :amonth AND DateSQL.day BETWEEN" +
            ":bdate and :adate GROUP BY value order by value LIMIT 1"  )
    String getMostCommonNote(String type, int byear, int ayear, int bmonth, int amonth, int bdate, int adate);
}