package br.edu.utfpr.vanderleyjunioralunos.vacineme.entities;

import java.util.Date;

public class Register {

    private Vaccine vaccine;
    private Person person;
    private Date dataVacina;
    private Date dataProxVacina;
    private int imagem;

    public Register(){}

    public Register(Vaccine vaccine, Person person, Date dataVacina, Date dataProxVacina, int imagem) {
        this.vaccine = vaccine;
        this.person = person;
        this.dataVacina = dataVacina;
        this.dataProxVacina = dataProxVacina;
        this.imagem = imagem;
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

    public Date getDataVacina() {
        return dataVacina;
    }

    public void setDataVacina(Date dataVacina) {
        this.dataVacina = dataVacina;
    }

    public Date getDataProxVacina() {
        return dataProxVacina;
    }

    public void setDataProxVacina(Date dataProxVacina) {
        this.dataProxVacina = dataProxVacina;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }
}
