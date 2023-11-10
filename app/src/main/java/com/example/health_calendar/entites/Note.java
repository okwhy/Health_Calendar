package com.example.health_calendar.entites;

import androidx.room.Entity;
import androidx.room.ForeignKey;
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
@Entity(foreignKeys = @ForeignKey
        (entity = Date.class, parentColumns = "id", childColumns = "id_fkdate",onDelete = ForeignKey.CASCADE))
public class Note implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id = 0;

    private String type;
    private String value;

    private long id_fkdate;
}
