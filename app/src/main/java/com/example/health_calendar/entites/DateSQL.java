package com.example.health_calendar.entites;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class DateSQL implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id = 0;
    private int year;
    private byte month;
    private byte day;

    public DateSQL(int year, byte month, byte day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Ignore
    public String getDateString(){
        return day+"-"+month+"-"+year;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(byte year) {
        this.year = year;
    }

    public byte getMonth() {
        return month;
    }

    public void setMonth(byte month) {
        this.month = month;
    }

    public byte getDay() {
        return day;
    }

    public void setDay(byte day) {
        this.day = day;
    }
}
