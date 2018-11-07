package br.edu.utfpr.vanderleyjunioralunos.vacineme.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "vaccines")
public class Vaccine {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String description;

    @NonNull
    private String lotNumber;

    @NonNull
    private String laboratorio;

    public Vaccine() {}

    @Ignore
    public Vaccine(String description, String laboratorio, String lotNumber) {
        this.description = description;
        this.lotNumber = lotNumber;
        this.laboratorio = laboratorio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(@NonNull String lotNumber) {
        this.lotNumber = lotNumber;
    }

    @NonNull
    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(@NonNull String laboratorio) {
        this.laboratorio = laboratorio;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
