package com.example.health_calendar.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.health_calendar.entites.Date;

import java.util.List;

@Dao
public interface DateDao {
    @Query("select * from date")
    List<Date> getAll();
    @Delete
    void delete(Date ... dates);
    @Insert
    long insert(Date date);
    @Query("select * from date where date.year =:year ")
    List<Date>getByYear(byte year);
    @Query("select * from date where date.year =:year and date.month =:month order by day ASC")
    List<Date>getByMonth(byte year,byte month);
}
