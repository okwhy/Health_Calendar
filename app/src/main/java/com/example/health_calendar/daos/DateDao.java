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
    List<DateSQL>getByYear(byte year);
    @Query("select * from DateSQL where DateSQL.year =:year and DateSQL.month =:month order by day")
    List<DateSQL>getByMonth(byte year, byte month);
    @Query("SELECT * FROM DateSQL WHERE DateSQL.year BETWEEN :byear AND :ayear AND " +
            "DateSQL.month BETWEEN :bmonth and :amonth AND DateSQL.day BETWEEN" +
            ":bdate and :adate")
    List<DateSQL>getBetween(byte byear, byte ayear, byte bmonth, byte amonth, byte bdate, byte adate);
    @Query("select * from DateSQL where DateSQL.year =:year and DateSQL.month =:month and DateSQL.day")
    DateWithNotes getDate(byte year, byte month, byte day);
}