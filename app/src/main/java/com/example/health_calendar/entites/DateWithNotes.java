package com.example.health_calendar.entites;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DateWithNotes {
    @Embedded public DateSQL dateSQL;
    @Relation(
            parentColumn = "id",
            entityColumn = "id_fkdate"
    )
    public List<Note>notes;
}
