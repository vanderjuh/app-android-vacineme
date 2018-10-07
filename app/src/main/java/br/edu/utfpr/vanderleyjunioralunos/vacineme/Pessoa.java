package br.edu.utfpr.vanderleyjunioralunos.vacineme;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Pessoa implements Parcelable {

    private String nome;
    private Date nascimento;
    private String genero;
    private Parentesco parentesco;

    public Pessoa() {}

    public Pessoa(Parcel in){
        this.nome = in.readString();
        this.nascimento = (Date)in.readValue(getClass().getClassLoader());
        this.genero = in.readString();
        this.parentesco = (Parentesco)in.readValue(getClass().getClassLoader());
    }

    public Pessoa(String nome, Date nascimento, String genero, Parentesco parentesco) {
        this.nome = nome;
        this.nascimento = nascimento;
        this.genero = genero;
        this.parentesco = parentesco;
    }

    public static final Creator<Pessoa> CREATOR = new Creator<Pessoa>() {
        @Override
        public Pessoa createFromParcel(Parcel in) {
            return new Pessoa(in);
        }

        @Override
        public Pessoa[] newArray(int size) {
            return new Pessoa[size];
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

    public Parentesco getParentesco() {
        return parentesco;
    }

    public void setParentesco(Parentesco parentesco) {
        this.parentesco = parentesco;
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
        dest.writeValue(this.parentesco);
    }
}
