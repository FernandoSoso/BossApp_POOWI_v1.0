package br.csi.service;

import br.csi.dao.CaminhaoDAO;
import br.csi.dao.MotoristaDAO;
import br.csi.dao.Motorista_CaminhaoDAO;
import br.csi.model.Motorista;
import br.csi.model.Motorista_Caminhao;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MotoristaService {

    private final MotoristaDAO motoristaDAO = new MotoristaDAO();
    private final Motorista_CaminhaoDAO motorista_caminhaoDAO = new Motorista_CaminhaoDAO();

    public ArrayList<Motorista> selectAll() {
        ArrayList<Motorista> motoristas = motoristaDAO.selectAll();
        int i = 0;
        while (i < motoristas.size()){
            Motorista motorista = motoristas.get(i);
            if (motorista.getCaminhao() == null){
                Motorista_Caminhao relacao = motorista_caminhaoDAO.selectByCod_caminhao(motorista.getCod());
                motorista.setCaminhao(new CaminhaoDAO().selectUnique(relacao.getCodMotorista()));
            }
            i++;
        }

        return motoristaDAO.selectAll();
    }

    public Motorista selectUnique(int cod) {
        Motorista motorista = motoristaDAO.selectUnique(cod);

        if (motorista.getCaminhao() == null){
            Motorista_Caminhao relacao = motorista_caminhaoDAO.selectByCod_caminhao(motorista.getCod());
            motorista.setCaminhao(new CaminhaoDAO().selectUnique(relacao.getCodMotorista()));
        }

        return motoristaDAO.selectUnique(cod);
    }

    public boolean insert(@NotNull String nome, String cpf, String endereco, String telefonePrincipal, @NotNull String telefoneAlternativo, String telefoneAlternativo2, int codCaminhao) {
        Motorista motorista = new Motorista(nome, cpf, endereco, telefonePrincipal, telefoneAlternativo, telefoneAlternativo2);
        int codMotorista = motoristaDAO.insert(motorista);

        if (codCaminhao == -1){
            return codMotorista != -1;
        }
        else{
            return trocarRelacionamento(codMotorista, codCaminhao) && codMotorista != -1;
        }
    }

    private boolean trocarRelacionamento(int codMotorista, int codCaminhao) {
        Motorista_Caminhao relacaoNova = new Motorista_Caminhao(codMotorista, codCaminhao);
        Motorista_Caminhao relacaoAntiga = motorista_caminhaoDAO.selectByCod_caminhao(codCaminhao);

        if (relacaoAntiga != null){
            if (relacaoAntiga.getCodMotorista() == codMotorista) {
                return true;
            }
            else {
                motorista_caminhaoDAO.delete(relacaoAntiga);
            }
        }

        return motorista_caminhaoDAO.insert(relacaoNova);

    }

    public boolean update(int codMotorista, @NotNull String nome, String cpf, String endereco, String telefonePrincipal, @NotNull String telefoneAlternativo, String telefoneAlternativo2, int codCaminhao) {
        Motorista motorista = new Motorista(nome, cpf, endereco, telefonePrincipal, telefoneAlternativo, telefoneAlternativo2);

        return motoristaDAO.update(motorista) && trocarRelacionamento(codMotorista, codCaminhao);
    }

    public boolean delete(int codMotorista){
        Motorista_Caminhao relacao = motorista_caminhaoDAO.selectByCod_motorista(codMotorista);

        if (motoristaDAO.selectUnique(codMotorista) == null){
            throw new IllegalArgumentException("Motorista não encontrado");
        }
        else{
            if (relacao != null){
                return motoristaDAO.delete(codMotorista) && motorista_caminhaoDAO.delete(relacao);
            }
            else{
                return motoristaDAO.delete(codMotorista);
            }
        }
    }

}
