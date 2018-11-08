package br.edu.utfpr.vanderleyjunioralunos.vacineme.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
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
    private int relationship;

    public Person() {}

    @Ignore
    public Person(String name, Date dateOfBorn, String gender, int relationship) {
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
    public int getRelationship() {
        return relationship;
    }

    public void setRelationship(@NonNull int relationship) {
        this.relationship = relationship;
    }

    @Override
    public String toString() {
        return this.name.toUpperCase();
    }

}
