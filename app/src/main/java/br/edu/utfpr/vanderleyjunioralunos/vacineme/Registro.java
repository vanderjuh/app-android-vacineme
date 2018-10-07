package br.edu.utfpr.vanderleyjunioralunos.vacineme;

import java.util.Date;

public class Registro {

    private Vacina vacina;
    private Pessoa pessoa;
    private Date dataVacina;
    private Date dataProxVacina;
    private int imagem;

    public Registro(){}

    public Registro(Vacina vacina, Pessoa pessoa, Date dataVacina, Date dataProxVacina, int imagem) {
        this.vacina = vacina;
        this.pessoa = pessoa;
        this.dataVacina = dataVacina;
        this.dataProxVacina = dataProxVacina;
        this.imagem = imagem;
    }

    public Vacina getVacina() {
        return vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
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
