package br.csi.model;

import br.csi.dao.DespesaDAO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class Frete {

    public Frete(int cod, String origem, Date origem_data, String destino, Date destino_data, double valor_tonelada, double peso, String observacao, String estado, Motorista motorista, Caminhao caminhao) {
        this.cod = cod;
        this.origem = origem;
        this.origem_data = origem_data;
        this.destino = destino;
        this.destino_data = destino_data;
        this.valor_tonelada = valor_tonelada;
        this.peso = peso;
        this.observacao = observacao;
        this.estado = estado;
        this.motorista = motorista;
        this.caminhao = caminhao;
    }

    private int cod;
    private String origem;
    private Date origem_data;
    private String destino;
    private Date destino_data;
    private double valor_tonelada;
    private double peso;
    private String observacao;
    private String estado;
    private Motorista motorista;
    private Caminhao caminhao;

    private double valorLiquido;
    private double valorBruto;

    public void setValorLiquido() {
        this.valorLiquido = this.valorBruto;
        new DespesaDAO().selectAllValores(this.cod).forEach(despesa -> {
            this.valorLiquido -= despesa.getValor();
        });
    }

    public void setValorBruto() {
        this.valorBruto = this.valor_tonelada * this.peso;
    }
}