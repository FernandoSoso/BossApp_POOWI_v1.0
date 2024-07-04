package br.csi.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
public class Motorista_Caminhao {

    public Motorista_Caminhao(int codMotorista, int codCaminhao, Date dataInicio) {
        this.codMotorista = codMotorista;
        this.codCaminhao = codCaminhao;
        this.dataInicio = dataInicio;
    }

    public Motorista_Caminhao(int codMotorista, int codCaminhao) {
        this.codMotorista = codMotorista;
        this.codCaminhao = codCaminhao;
    }

    private final int codMotorista;
    private final int codCaminhao;
    private Date dataInicio;
}
