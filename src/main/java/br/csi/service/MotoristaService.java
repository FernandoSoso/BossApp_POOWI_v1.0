package br.csi.service;

import br.csi.dao.CaminhaoDAO;
import br.csi.dao.MotoristaDAO;
import br.csi.dao.Motorista_CaminhaoDAO;
import br.csi.model.Caminhao;
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

    public boolean insert(@NotNull String nome, String endereco, String telefonePrincipal, @NotNull String telefoneAlternativo, String telefoneAlternativo2, String codCaminhao) {
        Motorista motorista = new Motorista(nome, endereco, telefonePrincipal, telefoneAlternativo, telefoneAlternativo2);
        int codMotorista = motoristaDAO.insert(motorista);

        if (codCaminhao.isBlank()){
            return false;
        }
        else{
            int codCaminhaoNumero = Integer.parseInt(codCaminhao);
            if (codCaminhaoNumero < 0){
                return true;
            }
            else{
                return trocarRelacionamento(codMotorista, codCaminhaoNumero);
            }
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

    public boolean update(@NotNull String codMotorista, @NotNull String nome, String endereco, String telefonePrincipal, @NotNull String telefoneAlternativo, String telefoneAlternativo2, String codCaminhao) {
        Motorista motorista = new Motorista(nome, endereco, telefonePrincipal, telefoneAlternativo, telefoneAlternativo2);

        if (codMotorista.isBlank()){
            return false;
        }
        else{
            int codMotoristaNumero = Integer.parseInt(codMotorista);
            if (codMotoristaNumero <= 0){
                return false;
            }
            else{
                if (motoristaDAO.update(motorista)){
                    if (codCaminhao.isBlank()){
                        return true;
                    }
                    else{
                        int codCaminhaoNumero = Integer.parseInt(codCaminhao);

                        if (codCaminhaoNumero <= 0){
                            return true;
                        }
                        else {
                            return trocarRelacionamento(codMotoristaNumero, codCaminhaoNumero);
                        }
                    }
                }
                else{
                    return false;
                }
            }
        }
    }

    public boolean delete(@NotNull String codMotorista){
        if (codMotorista.isBlank()){
            return false;
        }
        else{
            int codMotoristaNumero = Integer.parseInt(codMotorista);
            if (codMotoristaNumero <= 0){
                return false;
            }
            else {
                if (motoristaDAO.selectUnique(codMotoristaNumero) == null){
                    throw new IllegalArgumentException("Motorista não encontrado");
                }
                else{
                    Motorista_Caminhao relacao = motorista_caminhaoDAO.selectByCod_motorista(codMotoristaNumero);

                    if (relacao != null){
                        if (motorista_caminhaoDAO.delete(relacao)) {
                            return motoristaDAO.delete(codMotoristaNumero);
                        }
                        else{
                            return false;
                        }
                    }
                    else{
                        return motoristaDAO.delete(codMotoristaNumero);
                    }
                }
            }
        }
    }

}
