package br.csi.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Caminhao {

    public Caminhao(Integer cod) {
        this.cod = cod;
    }

    public Caminhao(Integer cod, String placa, String marca, String modelo, Integer ano, Integer capacidade, Double percentualMotorista, String estado) {
        this.cod = cod;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.capacidade = capacidade;
        this.percentualMotorista = percentualMotorista;
        this.estado = estado;
    }

    private Integer cod;
    private String placa;
    private String marca;
    private String modelo;
    private Integer ano;
    private Integer capacidade;
    private Double percentualMotorista;
    private String estado;
    private Motorista motorista;
    private Date dataMotorista;


    @Override
    public String toString() {
        return "Caminhao{" +
                "cod=" + cod +
                ", placa='" + placa + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano=" + ano +
                ", capacidade=" + capacidade +
                ", percentualMotorista=" + percentualMotorista +
                ", status='" + estado + '\'' +
                ", motorista=" + motorista +
                '}';
    }
}
