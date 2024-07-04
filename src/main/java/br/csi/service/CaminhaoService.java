package br.csi.service;

import br.csi.dao.CaminhaoDAO;
import br.csi.dao.MotoristaDAO;
import br.csi.dao.Motorista_CaminhaoDAO;
import br.csi.model.Caminhao;
import br.csi.model.Motorista_Caminhao;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CaminhaoService {
    private final CaminhaoDAO caminhaoDAO = new CaminhaoDAO();
    private final Motorista_CaminhaoDAO motorista_caminhaoDAO = new Motorista_CaminhaoDAO();

    public ArrayList<Caminhao> selectAll(String offset) {
        if (offset == null){
            return caminhaoDAO.selectAll(0);
        }
        else if (offset.isBlank()){
            return caminhaoDAO.selectAll(0);
        }
        else{
            int numOffset = Integer.parseInt(offset);
            return caminhaoDAO.selectAll(numOffset);
        }
    }

    public Caminhao selectUnique(@NotNull String cod) {
        int codNumero;
        if (cod.isBlank()){
            return null;
        }
        else {
            codNumero = Integer.parseInt(cod);
            if (codNumero <= 0){
                return null;
            }
        }

        Caminhao caminhao = caminhaoDAO.selectUnique(codNumero);
        if (caminhao.getMotorista() == null){
            Motorista_Caminhao relacao = motorista_caminhaoDAO.selectByCod_caminhao(caminhao.getCod());
            caminhao.setMotorista(new MotoristaDAO().selectUnique(relacao.getCodMotorista()));
        }

        return caminhao;
    }

    public boolean persist(@NotNull String operacao, String codCaminhao,@NotNull String placa, String marca, String modelo, String ano, String capacidade, @NotNull String percentualMotorista, @NotNull String status, String codMotorista) {
        if (!validarCampos(operacao,placa, marca, modelo, ano, capacidade, percentualMotorista, status)){
            return false;
        }

        int codCaminhaoNumero;
        int anoNumero;
        int capacidadeNumero;
        double percentualMotoristaNumero;

        if(marca.isBlank()){
            marca = null;
        }
        if(modelo.isBlank()){
            modelo = null;
        }

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

        if (codCaminhao == null){
            codCaminhaoNumero = -1;
        }
        else if (codCaminhao.isBlank()) {
            codCaminhaoNumero = -1;
        }
        else{
            codCaminhaoNumero = Integer.parseInt(codCaminhao);
        }

        Caminhao caminhao = new Caminhao(codCaminhaoNumero, placa, marca, modelo, anoNumero, capacidadeNumero, percentualMotoristaNumero, status);

        if (operacao.equals("update")){
            if (!caminhaoDAO.update(caminhao)){
                return false;
            }
        }
        else if (operacao.equals("insert")){
            codCaminhaoNumero = caminhaoDAO.insert(caminhao);

            if (codCaminhaoNumero < 0){
                return false;
            }
        }

        return gerarRelacionamento(codCaminhaoNumero, codMotorista);
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

    @NotNull
    private Boolean gerarRelacionamento(int codCaminhao, String codMotorista) {
        if (codMotorista == null){
            return true;
        }
        else if (codMotorista.isBlank()){
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

    public boolean delete(@NotNull String codCaminhao){
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

    private boolean validarCampos(String operacao,String placa, String marca, String modelo, String ano, String capacidade, String percentualMotorista, String status){
        if (!(operacao.equals("insert") || operacao.equals("update"))) {
            return false;
        }
        else if (placa.isBlank()){
            return false;
        }
        else if (placa.length() > 11 || marca.length() > 50 || modelo.length() > 50 || ano.length() > 4 || capacidade.length() > 5 || percentualMotorista.length() > 5 || status.length() > 1){
            return false;
        }

        return true;
    }
}
