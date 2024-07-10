package br.csi.model;

import br.csi.dao.CaminhaoDAO;
import br.csi.dao.DespesaDAO;
import br.csi.util.Round;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;


@Getter
@Setter
public class Frete {

    public Frete(Integer cod, String origem, Date origemData, String destino, Date destinoData, Double valor_tonelada, Double peso, String observacao, String estado, Motorista motorista, Caminhao caminhao) {
        this.cod = cod;
        this.origem = origem;
        this.origemData = origemData;
        this.destino = destino;
        this.destinoData = destinoData;
        this.valorTonelada = valor_tonelada;
        this.peso = peso;
        this.observacao = observacao;
        this.estado = estado;
        this.motorista = motorista;
        this.caminhao = caminhao;
        this.setValorBruto();
    }

    private Integer cod;
    private String origem;
    private Date origemData;
    private String destino;
    private Date destinoData;
    private Double valorTonelada;
    private Double peso;
    private String observacao;
    private String estado;
    private Motorista motorista;
    private Caminhao caminhao;

    private Double valorLiquido;
    private Double valorBruto;
    private Double parteMotorista;

    public void setValorBruto() {
        this.valorBruto = Round.roundUp(this.valorTonelada * (this.peso) / 1000, 2);
    }
}