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

@ToString
@Entity
public class Note implements Serializable {
    public Note(String type, String value, long id_fkdate) {
        this.type = type;
        this.value = value;
        this.id_fkdate = id_fkdate;
    }

    public Note(long id, String type, String value, long id_fkdate) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.id_fkdate = id_fkdate;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", id_fkdate=" + id_fkdate +
                '}';
    }

    public Note() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getId_fkdate() {
        return id_fkdate;
    }


    @PrimaryKey(autoGenerate = true)
    private long id = 0;

    private String type;
    private String value;

    public void setId_fkdate(long id_fkdate) {
        this.id_fkdate = id_fkdate;
    }

    private long id_fkdate;
}
