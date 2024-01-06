package com.example.health_calendar.entites;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;


@ToString
@Entity
public class DateSQL implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id = 0;
    private int year;
    private int month;
    private int day;
@Ignore
    public DateSQL(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public DateSQL(long id, int year, int month, int day) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Ignore
    public String getDateString(){
        return day+"-"+month+"-"+year;
    }
    @Ignore
    public LocalDate getAsLocalDate(){return LocalDate.of(year,month,day);}
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
