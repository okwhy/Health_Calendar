package com.example.health_calendar.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

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
    List<DateSQL>getByMonth(int year, byte month);
    @Query("SELECT * FROM DateSQL WHERE DateSQL.year BETWEEN :byear AND :ayear AND " +
            "DateSQL.month BETWEEN :bmonth and :amonth AND DateSQL.day BETWEEN" +
            ":bdate and :adate")
    List<DateSQL>getBetween(int byear, int ayear, byte bmonth, byte amonth, byte bdate, byte adate);

    @Query("select * from DateSQL where DateSQL.year =:year and DateSQL.month =:month and DateSQL.day =:day")
    DateWithNotes getDate(int year, byte month, byte day);
    @Query("select * from DateSQL where DateSQL.year =:year and DateSQL.month =:month and DateSQL.day =:day")
    DateSQL getDateNoNotes(int year, byte month, byte day);
}