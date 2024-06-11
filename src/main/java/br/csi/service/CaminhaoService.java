package br.csi.service;

import br.csi.dao.CaminhaoDAO;
import br.csi.dao.MotoristaDAO;
import br.csi.dao.Motorista_CaminhaoDAO;
import br.csi.model.Caminhao;
import br.csi.model.Motorista_Caminhao;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CaminhaoService {
    private final CaminhaoDAO caminhaoDAO = new CaminhaoDAO();
    private final Motorista_CaminhaoDAO motorista_caminhaoDAO = new Motorista_CaminhaoDAO();

    public ArrayList<Caminhao> selectAll() {
        ArrayList<Caminhao> caminhoes = caminhaoDAO.selectAll();
        int i = 0;
        while (i < caminhoes.size()){
            Caminhao caminhao = caminhoes.get(i);
            if (caminhao.getMotorista() == null){
                Motorista_Caminhao relacao = motorista_caminhaoDAO.selectByCod_caminhao(caminhao.getCod());
                if (relacao != null){
                    caminhao.setMotorista(new MotoristaDAO().selectUnique(relacao.getCodMotorista()));
                }
            }
            i++;
        }

        return caminhoes;
    }

    public Caminhao selectUnique(int cod) {
        Caminhao caminhao = caminhaoDAO.selectUnique(cod);
        if (caminhao.getMotorista() == null){
            Motorista_Caminhao relacao = motorista_caminhaoDAO.selectByCod_caminhao(caminhao.getCod());
            caminhao.setMotorista(new MotoristaDAO().selectUnique(relacao.getCodMotorista()));
        }

        return caminhao;
    }

    public boolean insert(String placa, String marca, String modelo, String ano, String capacidade, String percentualMotorista, String status, String codMotorista) {
        int anoNumero;
        int capacidadeNumero;
        double percentualMotoristaNumero;

        if (ano.isBlank()){
            anoNumero = -1;
        }
        else{
            anoNumero = Integer.parseInt(ano);
        }

        if (capacidade.isBlank()){
            capacidadeNumero = -1;
        }
        else{
            capacidadeNumero = Integer.parseInt(capacidade);
        }

        if (percentualMotorista.isBlank()){
            percentualMotoristaNumero = 0;
        }
        else{
            percentualMotoristaNumero = Double.parseDouble(percentualMotorista);
        }

        Caminhao caminhao = new Caminhao(placa, marca, modelo, anoNumero, capacidadeNumero, percentualMotoristaNumero, status);
        int codCaminhao = caminhaoDAO.insert(caminhao);

        if (codCaminhao == -1){
            return false;
        }
        else{
            if (codMotorista.isBlank()){
                return true;
            }
            else{
                int codMotoristaNumero = Integer.parseInt(codMotorista);
                if (codMotoristaNumero <= 0){
                    return true;
                }
                else {
                    return trocarRelacionamento(codMotoristaNumero, codCaminhao);
                }
            }
        }
    }

    private boolean trocarRelacionamento(int codMotorista, int codCaminhao) {

        Motorista_Caminhao relacaoNova = new Motorista_Caminhao(codMotorista, codCaminhao);
        Motorista_Caminhao relacaoAntiga = motorista_caminhaoDAO.selectByCod_motorista(codMotorista);

        if (relacaoAntiga != null){
            if (relacaoAntiga.getCodCaminhao() == codCaminhao){
                return true;
            }
            else {
                motorista_caminhaoDAO.delete(relacaoAntiga);
            }
        }

        return motorista_caminhaoDAO.insert(relacaoNova);
    }

    public boolean update(int codCaminhao, @NotNull String placa, String marca, String modelo, String ano, String capacidade, @NotNull String percentualMotorista, @NotNull String status, String codMotorista) {
        int anoNumero;
        int capacidadeNumero;
        double percentualMotoristaNumero;

        if (ano.isBlank()){
            anoNumero = -1;
        }
        else{
            anoNumero = Integer.parseInt(ano);
        }

        if (capacidade.isBlank()){
            capacidadeNumero = -1;
        }
        else{
            capacidadeNumero = Integer.parseInt(capacidade);
        }

        if (percentualMotorista.isBlank()){
            percentualMotoristaNumero = 0;
        }
        else{
            percentualMotoristaNumero = Double.parseDouble(percentualMotorista);
        }

        Caminhao caminhao = new Caminhao(codCaminhao, placa, marca, modelo, anoNumero, capacidadeNumero, percentualMotoristaNumero, status);

        if (caminhaoDAO.update(caminhao)){
            if (codMotorista.isBlank()){
                return true;
            }
            else{
                int codMotoristaNumero = Integer.parseInt(codMotorista);

                if (codMotoristaNumero <= 0){
                    return true;
                }
                else {
                    return trocarRelacionamento(codMotoristaNumero, codCaminhao);
                }
            }
        }
        else{
            return false;
        }
    }

    public boolean delete(String codCaminhao){
        if (codCaminhao.isBlank()){
            return false;
        }
        else {
            int codCaminhaoNumero = Integer.parseInt(codCaminhao);
            if (codCaminhaoNumero <= 0){
                return false;
            }
            else {
                Motorista_Caminhao relacao = motorista_caminhaoDAO.selectByCod_motorista(codCaminhaoNumero);

                if (caminhaoDAO.selectUnique(codCaminhaoNumero) == null){
                    throw new IllegalArgumentException("Caminhão não encontrado");
                }
                else{
                    if (relacao != null){
                        if (motorista_caminhaoDAO.delete(relacao)) {
                            return caminhaoDAO.delete(codCaminhaoNumero);
                        }
                        else{
                            return false;
                        }
                    }
                    else{
                        return caminhaoDAO.delete(codCaminhaoNumero);
                    }
                }
            }
        }


    }
}
