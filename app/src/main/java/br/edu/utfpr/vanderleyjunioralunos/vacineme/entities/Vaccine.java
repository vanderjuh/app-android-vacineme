package br.edu.utfpr.vanderleyjunioralunos.vacineme.entities;

public class Vaccine {

    private String descricao;
    private String lote;
    private String laboratorio;

    public Vaccine() {}

    public Vaccine(String descricao, String lote, String laboratorio) {
        this.descricao = descricao;
        this.lote = lote;
        this.laboratorio = laboratorio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
