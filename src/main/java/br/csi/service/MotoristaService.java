package br.csi.service;

import br.csi.dao.CaminhaoDAO;
import br.csi.dao.MotoristaDAO;
import br.csi.dao.Motorista_CaminhaoDAO;
import br.csi.model.Motorista;
import br.csi.model.Motorista_Caminhao;
import br.csi.util.ParamConverter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MotoristaService {

    private final MotoristaDAO motoristaDAO = new MotoristaDAO();
    private final Motorista_CaminhaoDAO motorista_caminhaoDAO = new Motorista_CaminhaoDAO();
    private final ParamConverter paramConverter = new ParamConverter();

    public ArrayList<Motorista> selectAll(String offset) {
        Integer offsetNumber = paramConverter.convertStringToInt(offset);

        if (offsetNumber == null){
            return null;
        }
        else {
            return motoristaDAO.selectAll(offsetNumber);
        }
    }

    public Motorista selectUnique(@NotNull String codMotorista) {
        Integer codMotoristaNumber = paramConverter.convertStringToInt(codMotorista);

        if (codMotoristaNumber == null || codMotoristaNumber <= 0){
            return null;
        }
        else{
            Motorista motorista = motoristaDAO.selectUnique(codMotoristaNumber);

            Motorista_Caminhao relacao = motorista_caminhaoDAO.selectByCod_motorista(motorista.getCod());

            if (relacao != null){
                motorista.setCaminhao(new CaminhaoDAO().selectUnique(relacao.getCodCaminhao()));
            }
            return motorista;
        }
    }

    public boolean persist(@NotNull String operacao, String codMotorista, @NotNull String nome,  String endereco,
                           String telefonePrincipal, @NotNull String telefoneAlternativo, String telefoneAlternativo2,
                           String codCaminhao) {
        if (!validarCampos(operacao,nome, endereco, telefonePrincipal, telefoneAlternativo, telefoneAlternativo2)){
            return false;
        }

        Integer codMotoristaNumber = paramConverter.convertStringToInt(codMotorista);
        Integer codCaminhaoNumber = paramConverter.convertStringToInt(codCaminhao);
        endereco = paramConverter.convertBlankStringToNull(endereco);
        telefoneAlternativo = paramConverter.convertBlankStringToNull(telefoneAlternativo);
        telefoneAlternativo2 = paramConverter.convertBlankStringToNull(telefoneAlternativo2);

        Motorista motorista = new Motorista(codMotoristaNumber,nome, endereco, telefonePrincipal, telefoneAlternativo, telefoneAlternativo2);

        if (operacao.equals("update")){
            return motoristaDAO.update(motorista);
        }
        else if (operacao.equals("insert")){
            return motoristaDAO.insert(motorista) < 0;
        }

        return gerarRelacionamento(codMotoristaNumber, codCaminhaoNumber);
    }


    public boolean delete(@NotNull String codMotorista){
        Integer codMotoristaNumber = paramConverter.convertStringToInt(codMotorista);

        if (codMotoristaNumber == null || codMotoristaNumber <= 0){
            return false;
        }
        else{
            if (motoristaDAO.selectUnique(codMotoristaNumber) == null){
                throw new IllegalArgumentException("Motorista não encontrado");
            }
            else{
                Motorista_Caminhao relacao = motorista_caminhaoDAO.selectByCod_motorista(codMotoristaNumber);

                if (relacao != null){
                    if (!motorista_caminhaoDAO.delete(relacao)) {
                        return false;
                    }
                }

                return motoristaDAO.delete(codMotoristaNumber);
            }
        }
    }

    private boolean trocarRelacionamento(Integer codMotorista, Integer codCaminhao) {
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

    private boolean gerarRelacionamento(Integer codMotorista, Integer codCaminhao){
        if (codCaminhao == null || codCaminhao <= 0){
            return true;
        }
        else{
            return trocarRelacionamento(codMotorista, codCaminhao);
        }
    }

    private boolean validarCampos(String operacao,String nome, String endereco, String telefonePrincipal, String telefoneAlternativo, String telefoneAlternativo2){
        if (!(operacao.equals("insert") || operacao.equals("update"))) {
            return false;
        }
        else if (nome.isBlank() || telefonePrincipal.isBlank() ){
            return false;
        }
        else return endereco.length() <= 100 && telefoneAlternativo.length() <= 15 && telefoneAlternativo2.length() <= 15
                    && telefonePrincipal.length() <= 15;
    }
}
