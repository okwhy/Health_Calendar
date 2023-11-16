package com.example.health_calendar.entites;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Entity
public class Date implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id = 0;
    private byte year;
    private byte month;
    private byte day;

}