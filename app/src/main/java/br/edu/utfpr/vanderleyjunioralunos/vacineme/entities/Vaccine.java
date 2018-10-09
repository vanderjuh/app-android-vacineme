package br.edu.utfpr.vanderleyjunioralunos.vacineme.entities;

public class Vaccine {

    private String description;
    private String lotNumber;
    private String laboratorio;

    public Vaccine() {}

    public Vaccine(String description, String laboratorio, String lotNumber) {
        this.description = description;
        this.lotNumber = lotNumber;
        this.laboratorio = laboratorio;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
