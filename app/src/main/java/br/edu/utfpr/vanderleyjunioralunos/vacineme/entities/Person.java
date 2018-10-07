package br.edu.utfpr.vanderleyjunioralunos.vacineme.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Person implements Parcelable {

    private String nome;
    private Date nascimento;
    private String genero;
    private Relationship relationship;

    public Person() {}

    public Person(Parcel in){
        this.nome = in.readString();
        this.nascimento = (Date)in.readValue(getClass().getClassLoader());
        this.genero = in.readString();
        this.relationship = (Relationship)in.readValue(getClass().getClassLoader());
    }

    public Person(String nome, Date nascimento, String genero, Relationship relationship) {
        this.nome = nome;
        this.nascimento = nascimento;
        this.genero = genero;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    @Override
    public String toString() {
        return this.nome.toUpperCase();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nome);
        dest.writeValue(this.nascimento);
        dest.writeString(this.genero);
        dest.writeValue(this.relationship);
    }
}
