package br.csi.model;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class Caminhao {

    private int cod;
    private String placa;
    private String marca;
    private String modelo;
    private Integer ano;
    private Integer capacidade;
    private Double percentualMotorista;
    private String status;
    private Motorista motorista;
    private Date dataMotorista;

    public Caminhao() {
    }

    public Caminhao(int cod, String placa, String marca, String modelo, int ano, int capacidade, double percentualMotorista, String status) {
        if (placa.isBlank() || status.isBlank() || percentualMotorista < 0 || percentualMotorista > 100 || cod < 0){
            throw new IllegalArgumentException("Campos obrigatórios inválidos!");
        }

        this.cod = cod;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.capacidade = capacidade;
        this.percentualMotorista = percentualMotorista;
        this.status = status;
    }

    public Caminhao(String placa, String marca, String modelo, int ano, int capacidade, double percentualMotorista, String status) {
        if (placa.isBlank() || status.isBlank() || percentualMotorista < 0 || percentualMotorista > 100){
            throw new IllegalArgumentException("Campos obrigatórios inválidos ou não preenchidos");
        }

        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.capacidade = capacidade;
        this.percentualMotorista = percentualMotorista;
        this.status = status;
    }

    public int getCod() {
        return cod;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public Double getPercentualMotorista() {
        return percentualMotorista;
    }

    public void setPercentualMotorista(double percentualMotorista) {
        this.percentualMotorista = percentualMotorista;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public Date getDataMotorista() {
        return dataMotorista;
    }

    public void setDataMotorista(Date dataMotorista) {
        this.dataMotorista = dataMotorista;
    }

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
                ", status='" + status + '\'' +
                ", motorista=" + motorista +
                '}';
    }
}
