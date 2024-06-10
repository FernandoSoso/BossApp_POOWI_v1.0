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

    public boolean insert(@NotNull String placa, String marca, String modelo, int ano, int capacidade, @NotNull double percentualMotorista, @NotNull String status, int codMotorista) {
        Caminhao caminhao = new Caminhao(placa, marca, modelo, ano, capacidade, percentualMotorista, status);
        int codCaminhao = caminhaoDAO.insert(caminhao);

        if (codMotorista == -1){
            return codCaminhao != -1;
        }
        else{
            return trocarRelacionamento(codMotorista, codCaminhao) && codCaminhao != -1;
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

    public boolean update(int codCaminhao, @NotNull String placa, String marca, String modelo, int ano, int capacidade, @NotNull double percentualMotorista, @NotNull String status, int codMotorista) {
        Caminhao caminhao = new Caminhao(codCaminhao, placa, marca, modelo, ano, capacidade, percentualMotorista, status);

        return caminhaoDAO.update(caminhao) && trocarRelacionamento(codMotorista, codCaminhao);

    }

    public boolean delete(int codCaminhao){
        Motorista_Caminhao relacao = motorista_caminhaoDAO.selectByCod_motorista(codCaminhao);

        if (caminhaoDAO.selectUnique(codCaminhao) == null){
            throw new IllegalArgumentException("Caminhão não encontrado");
        }
        else{
            if (relacao != null){
                return caminhaoDAO.delete(codCaminhao) && motorista_caminhaoDAO.delete(relacao);
            }
            else{
                return caminhaoDAO.delete(codCaminhao);
            }
        }
    }
}
