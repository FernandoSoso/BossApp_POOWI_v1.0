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

    public ArrayList<Motorista> selectAll(String offset) {
        if (offset == null){
            return null;
        }
        else if (offset.isBlank()){
            return null;
        }
        else {
            int offsetNumero = Integer.parseInt(offset);
            return motoristaDAO.selectAll(offsetNumero);
        }
    }

    public Motorista selectUnique(int cod) {
        Motorista motorista = motoristaDAO.selectUnique(cod);

        if (motorista.getCaminhao() == null){
            Motorista_Caminhao relacao = motorista_caminhaoDAO.selectByCod_caminhao(motorista.getCod());
            motorista.setCaminhao(new CaminhaoDAO().selectUnique(relacao.getCodMotorista()));
        }

        return motorista;
    }

    public boolean persist(@NotNull String operacao, String codMotorista, @NotNull String nome,  String endereco, String telefonePrincipal, @NotNull String telefoneAlternativo, String telefoneAlternativo2, String codCaminhao) {
        int codMotoristaNumero;

        if (!validarCampos(operacao,nome, endereco, telefonePrincipal, telefoneAlternativo, telefoneAlternativo2)){
            return false;
        }

        if(endereco.isBlank()){
            endereco = null;
        }

        if(telefoneAlternativo.isBlank()){
            telefoneAlternativo = null;
        }

        if(telefoneAlternativo2.isBlank()){
            telefoneAlternativo2 = null;
        }

        if (codMotorista == null){
            codMotoristaNumero = -1;
        }
        else if (codMotorista.isBlank()){
            codMotoristaNumero = -1;
        }
        else{
            codMotoristaNumero = Integer.parseInt(codMotorista);
        }


        Motorista motorista = new Motorista(codMotoristaNumero,nome, endereco, telefonePrincipal, telefoneAlternativo, telefoneAlternativo2);

        if (operacao.equals("update")){
            if (!motoristaDAO.update(motorista)){
                return false;
            }
        }
        else if (operacao.equals("insert")){
            codMotoristaNumero = motoristaDAO.insert(motorista);

            if (codMotoristaNumero < 0){
                return false;
            }
        }

        return gerarRelacionamento(codMotoristaNumero, codCaminhao);
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


    private boolean validarCampos(String operacao,String nome, String endereco, String telefonePrincipal, String telefoneAlternativo, String telefoneAlternativo2){
        if (!(operacao.equals("insert") || operacao.equals("update"))) {
            return false;
        }
        else if (nome.isBlank() || telefonePrincipal.isBlank() ){
            return false;
        }
        else if (endereco.length() > 100 || telefoneAlternativo.length() > 15 || telefoneAlternativo2.length() > 15 || telefonePrincipal.length() > 15){
            return false;
        }

        return true;
    }

    private boolean gerarRelacionamento(int codMotorista, String codCaminhao){
        if (codCaminhao == null){
            return true;
        }
        else if (codCaminhao.isBlank()){
            return true;
        }
        else{
            int codCaminhaoNumero = Integer.parseInt(codCaminhao);

            if (codCaminhaoNumero <= 0){
                return true;
            }
            else {
                return trocarRelacionamento(codMotorista, codCaminhaoNumero);
            }
        }
    }
}
