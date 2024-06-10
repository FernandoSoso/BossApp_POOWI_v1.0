package br.csi.model;

import java.sql.Date;
import java.util.ArrayList;

public class Motorista_Caminhao {
    private final int codMotorista;
    private final int codCaminhao;
    private Date dataInicio;

    public Motorista_Caminhao(int codMotorista, int codCaminhao, Date dataInicio) {
        this.codMotorista = codMotorista;
        this.codCaminhao = codCaminhao;
        this.dataInicio = dataInicio;
    }

    public Motorista_Caminhao(int codMotorista, int codCaminhao) {
        this.codMotorista = codMotorista;
        this.codCaminhao = codCaminhao;
    }

    public int getCodMotorista() {
        return codMotorista;
    }

    public int getCodCaminhao() {
        return codCaminhao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }
}
