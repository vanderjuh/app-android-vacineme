package br.edu.utfpr.vanderleyjunioralunos.vacineme.entities;

import java.util.Date;

public class Register {

    private Vaccine vaccine;
    private Person person;
    private Date vaccineDate;
    private Date nextDateVaccine;
    private int iconVaccine;

    public Register(){}

    public Register(Vaccine vaccine, Person person, Date vaccineDate, Date nextDateVaccine, int iconVaccine) {
        this.vaccine = vaccine;
        this.person = person;
        this.vaccineDate = vaccineDate;
        this.nextDateVaccine = nextDateVaccine;
        this.iconVaccine = iconVaccine;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getVaccineDate() {
        return vaccineDate;
    }

    public void setVaccineDate(Date vaccineDate) {
        this.vaccineDate = vaccineDate;
    }

    public Date getNextDateVaccine() {
        return nextDateVaccine;
    }

    public void setNextDateVaccine(Date nextDateVaccine) {
        this.nextDateVaccine = nextDateVaccine;
    }

    public int getIconVaccine() {
        return iconVaccine;
    }

    public void setIconVaccine(int iconVaccine) {
        this.iconVaccine = iconVaccine;
    }
}
