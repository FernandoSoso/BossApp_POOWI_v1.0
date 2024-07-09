package br.csi.service;

import br.csi.dao.CaminhaoDAO;
import br.csi.dao.MotoristaDAO;
import br.csi.dao.Motorista_CaminhaoDAO;
import br.csi.model.Caminhao;
import br.csi.model.Motorista_Caminhao;
import br.csi.util.ParamConverter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CaminhaoService {
    private final CaminhaoDAO caminhaoDAO = new CaminhaoDAO();
    private final Motorista_CaminhaoDAO motorista_caminhaoDAO = new Motorista_CaminhaoDAO();

    private final ParamConverter paramConverter = new ParamConverter();

    public ArrayList<Caminhao> selectAll(String offset) {
        Integer offsetNumber = paramConverter.convertStringToInt(offset);

        if (offsetNumber == null){
            return caminhaoDAO.selectAll(0);
        }
        else{
            return caminhaoDAO.selectAll(offsetNumber);
        }
    }

    public Caminhao selectUnique(@NotNull String codCaminhao) {
        Integer codCaminhaoNumber = paramConverter.convertStringToInt(codCaminhao);

        if (codCaminhaoNumber == null){
            return null;
        }
        else if (codCaminhaoNumber <= 0){
            return null;
        }

        Caminhao caminhao = caminhaoDAO.selectUnique(codCaminhaoNumber);
        if (caminhao.getMotorista() == null){
            Motorista_Caminhao relacao = motorista_caminhaoDAO.selectByCod_caminhao(caminhao.getCod());
            caminhao.setMotorista(new MotoristaDAO().selectUnique(relacao.getCodMotorista()));
        }

        return caminhao;
    }

    public boolean persist(@NotNull String operacao, String codCaminhao,@NotNull String placa, String marca, String modelo, String ano, String capacidade, @NotNull String percentualMotorista, @NotNull String estado, String codMotorista) {
        if (!validarCampos(operacao,placa, marca, modelo, ano, capacidade, percentualMotorista, estado)){
            return false;
        }

        Integer codCaminhaoNumber = paramConverter.convertStringToInt(codCaminhao);
        Integer codMotoristaNumber = paramConverter.convertStringToInt(codMotorista);
        Integer anoNumber = paramConverter.convertStringToInt(ano);
        Integer capacidadeNumber = paramConverter.convertStringToInt(capacidade);
        Double percentualMotoristaNumber = paramConverter.convertStringToDouble(percentualMotorista);
        marca = paramConverter.convertBlankStringToNull(marca);
        modelo = paramConverter.convertBlankStringToNull(modelo);


        Caminhao caminhao = new Caminhao(codCaminhaoNumber, placa, marca, modelo, anoNumber, capacidadeNumber, percentualMotoristaNumber, estado);

        if (operacao.equals("update")){
            if (!caminhaoDAO.update(caminhao)){
                return false;
            }
        }
        else if (operacao.equals("insert")){
            codCaminhaoNumber = caminhaoDAO.insert(caminhao);

            if (codCaminhaoNumber < 0){
                return false;
            }
        }

        return gerarRelacionamento(codCaminhaoNumber, codMotoristaNumber);
    }



    public boolean delete(@NotNull String codCaminhao){
        Integer codCaminhaoNumber = paramConverter.convertStringToInt(codCaminhao);

        if (codCaminhaoNumber == null){
            return false;
        }
        else {
            if (codCaminhaoNumber <= 0){
                return false;
            }
            else {
                Motorista_Caminhao relacao = motorista_caminhaoDAO.selectByCod_motorista(codCaminhaoNumber);

                if (caminhaoDAO.selectUnique(codCaminhaoNumber) == null){
                    throw new IllegalArgumentException("Caminhão não encontrado");
                }
                else{
                    if (relacao != null){
                        if (!motorista_caminhaoDAO.delete(relacao)) {
                            return false;
                        }
                    }

                    return caminhaoDAO.delete(codCaminhaoNumber);
                }
            }
        }
    }

    private boolean trocarRelacionamento(Integer codMotorista, Integer codCaminhao) {
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
    private Boolean gerarRelacionamento(Integer codCaminhao, Integer codMotorista) {
        if (codMotorista == null || codMotorista <= 0){
            return true;
        }
        else{
            return trocarRelacionamento(codMotorista, codCaminhao);
        }
    }

    private boolean validarCampos(String operacao,String placa, String marca, String modelo, String ano, String capacidade, String percentualMotorista, String estado){
        if (!(operacao.equals("insert") || operacao.equals("update"))) {
            return false;
        }
        else if (placa.isBlank()){
            return false;
        }
        else return placa.length() <= 11 && marca.length() <= 50 && modelo.length() <= 50 && ano.length() <= 4 && capacidade.length() <= 5
                    && percentualMotorista.length() <= 5 && estado.length() <= 1;
    }
}
