package br.edu.utfpr.vanderleyjunioralunos.vacineme.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "registers", foreignKeys = {
        @ForeignKey(entity = Vaccine.class, parentColumns = "id", childColumns = "vaccineId"),
        @ForeignKey(entity = Person.class, parentColumns = "id", childColumns = "personId", onDelete = CASCADE)})
public class Register {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(index = true)
    private int vaccineId;

    @NonNull
    @ColumnInfo(index = true)
    private int personId;

    @Ignore
    private Vaccine vaccine;

    @NonNull
    private Date vaccineDate;
    private Date nextDateVaccine;

    public Register(){}

    @Ignore
    public Register(int vaccineId, int personId,@NonNull Date vaccineDate, Date nextDateVaccine) {
        this.vaccineId = vaccineId;
        this.personId = personId;
        this.vaccineDate = vaccineDate;
        this.nextDateVaccine = nextDateVaccine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public int getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(@NonNull int vaccineId) {
        this.vaccineId = vaccineId;
    }

    @NonNull
    public int getPersonId() {
        return personId;
    }

    public void setPersonId(@NonNull int personId) {
        this.personId = personId;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    @NonNull
    public Date getVaccineDate() {
        return vaccineDate;
    }

    public void setVaccineDate(@NonNull Date vaccineDate) {
        this.vaccineDate = vaccineDate;
    }

    public Date getNextDateVaccine() {
        return nextDateVaccine;
    }

    public void setNextDateVaccine(Date nextDateVaccine) {
        this.nextDateVaccine = nextDateVaccine;
    }
}
