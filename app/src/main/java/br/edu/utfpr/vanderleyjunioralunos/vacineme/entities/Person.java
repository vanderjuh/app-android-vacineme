package br.edu.utfpr.vanderleyjunioralunos.vacineme.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "people")
public class Person {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;
    private Date dateOfBorn;
    private String gender;

    @NonNull
    private String relationship;

    public Person() {}

    @Ignore
    public Person(String name, Date dateOfBorn, String gender, String relationship) {
        this.name = name;
        this.dateOfBorn = dateOfBorn;
        this.gender = gender;
        this.relationship = relationship;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public Date getDateOfBorn() {
        return dateOfBorn;
    }

    public void setDateOfBorn(Date dateOfBorn) {
        this.dateOfBorn = dateOfBorn;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @NonNull
    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(@NonNull String relationship) {
        this.relationship = relationship;
    }

    @Override
    public String toString() {
        return this.name.toUpperCase();
    }

}
