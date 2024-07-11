package br.csi.model;

import br.csi.util.Round;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class Frete {

    public Frete() {
    }

    private Integer cod;
    private String origem;
    private String destino;
    private Double valorTonelada;
    private Double peso;
    private String observacao;
    private String estado;
    private Motorista motorista;
    private Caminhao caminhao;

    private Double valorLiquido;
    private Double valorBruto;
    private Double parteMotorista;

    public Frete(Integer cod, String origem, String destino, Double valor_tonelada, Double peso, String observacao, String estado, Motorista motorista, Caminhao caminhao, Double valorLiquido, Double parteMotorista) {
        this.cod = cod;
        this.origem = origem;
        this.destino = destino;
        this.valorTonelada = valor_tonelada;
        this.peso = peso;
        this.observacao = observacao;
        this.estado = estado;
        this.motorista = motorista;
        this.caminhao = caminhao;
        this.valorLiquido = valorLiquido;
        this.parteMotorista = parteMotorista;

        this.setValorBruto();
    }


    public Frete(Integer codFreteNumber, String origem, String destino, Double valorToneladaNumber, Double pesoNumber, String observacao, String estado, Motorista motorista, Caminhao caminhao) {
        this.cod = codFreteNumber;
        this.origem = origem;
        this.destino = destino;
        this.valorTonelada = valorToneladaNumber;
        this.peso = pesoNumber;
        this.observacao = observacao;
        this.estado = estado;
        this.motorista = motorista;
        this.caminhao = caminhao;

        setValorBruto();
    }

    public void setValorBruto() {
        this.valorBruto = Round.roundUp(this.valorTonelada * (this.peso) / 1000, 2);
    }

    @Override
    public String toString() {
        return "Frete{" +
                "cod=" + cod +
                ", origem='" + origem + '\'' +
                ", destino='" + destino + '\'' +
                ", valorTonelada=" + valorTonelada +
                ", peso=" + peso +
                ", observacao='" + observacao + '\'' +
                ", estado='" + estado + '\'' +
                ", motorista=" + motorista +
                ", caminhao=" + caminhao +
                ", valorLiquido=" + valorLiquido +
                ", valorBruto=" + valorBruto +
                ", parteMotorista=" + parteMotorista +
                '}';
    }
}