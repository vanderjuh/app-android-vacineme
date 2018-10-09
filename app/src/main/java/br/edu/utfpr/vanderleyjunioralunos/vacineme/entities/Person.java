package br.edu.utfpr.vanderleyjunioralunos.vacineme.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Person implements Parcelable {

    private String name;
    private Date dateOfBorn;
    private String gender;
    private Relationship relationship;

    public Person() {}

    public Person(Parcel in){
        this.name = in.readString();
        this.dateOfBorn = (Date)in.readValue(getClass().getClassLoader());
        this.gender = in.readString();
        this.relationship = (Relationship)in.readValue(getClass().getClassLoader());
    }

    public Person(String name, Date dateOfBorn, String gender, Relationship relationship) {
        this.name = name;
        this.dateOfBorn = dateOfBorn;
        this.gender = gender;
        this.relationship = relationship;
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
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

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    @Override
    public String toString() {
        return this.name.toUpperCase();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeValue(this.dateOfBorn);
        dest.writeString(this.gender);
        dest.writeValue(this.relationship);
    }
}
