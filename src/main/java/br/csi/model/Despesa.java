package br.csi.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class Despesa {

    public Despesa(int cod, String tipo, double valor, int cod_frete, Date data_insercao) {
        this.cod = cod;
        this.tipo = tipo;
        this.valor = valor;
        this.cod_frete = cod_frete;
        this.data_insercao = data_insercao;
    }

    private int cod;
    private String tipo;
    private double valor;
    private int cod_frete;
    private Date data_insercao;

}
