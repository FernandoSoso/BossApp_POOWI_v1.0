package br.csi.service;

import br.csi.dao.CaminhaoDAO;
import br.csi.dao.FreteDAO;
import br.csi.dao.MotoristaDAO;
import br.csi.model.Caminhao;
import br.csi.model.Frete;
import br.csi.model.Motorista;
import br.csi.util.ParamConverter;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FreteService {
    private final FreteDAO freteDAO = new FreteDAO();
    private final MotoristaDAO motoristaDAO = new MotoristaDAO();
    private final CaminhaoDAO caminhaoDAO = new CaminhaoDAO();
    private final ParamConverter paramConverter = new ParamConverter();

    public ArrayList<Frete> selectAll(String offset) {
        Integer offsetNumber = paramConverter.convertStringToInt(offset);

        if (offsetNumber == null){
            return freteDAO.selectAll(0);
        }
        else{
            return freteDAO.selectAll(offsetNumber);
        }
    }

    public Frete selectUnique(@NotNull String codFrete) {
        Integer codFreteNumber = paramConverter.convertStringToInt(codFrete);

        if (codFreteNumber == null || codFreteNumber <= 0){
            return null;
        }

        Frete frete = freteDAO.selectUnique(codFreteNumber);

        if (frete.getMotorista().getCod() > 0){
            Motorista motorista = motoristaDAO.selectUnique(frete.getMotorista().getCod());
            frete.setMotorista(motorista);
        }

        if (frete.getCaminhao().getCod() > 0){
            Caminhao caminhao = caminhaoDAO.selectUnique(frete.getCaminhao().getCod());
            frete.setCaminhao(caminhao);
        }

        return frete;
    }

    public boolean persist(@NotNull String operacao, String codFrete, @NotNull String origem, String origem_data, @NotNull String destino,
                           String destino_data, @NotNull String valorTonelada, @NotNull String peso, String observacao, @NotNull  String estado,
                           String codMotorista, String codCaminhao) {

        if (!validarCampos(operacao, origem, destino, valorTonelada, peso, observacao, estado, codMotorista, codCaminhao)){
            return false;
        }

        Integer codFreteNumber = paramConverter.convertStringToInt(codFrete);
        Integer codMotoristaNumber = paramConverter.convertStringToInt(codMotorista);
        Integer codCaminhaoNumber = paramConverter.convertStringToInt(codCaminhao);
        Double valorToneladaNumber = paramConverter.convertStringToDouble(valorTonelada);
        Double pesoNumber = paramConverter.convertStringToDouble(peso);
        Date origemDataDate = paramConverter.convertStringToDate(origem_data);
        Date destinoDataDate = paramConverter.convertStringToDate(destino_data);
        observacao = paramConverter.convertBlankStringToNull(observacao);

        Caminhao caminhao = new Caminhao(codCaminhaoNumber);
        Motorista motorista = new Motorista(codMotoristaNumber);
        Frete frete = new Frete(codFreteNumber, origem, origemDataDate, destino, destinoDataDate, valorToneladaNumber, pesoNumber, observacao, estado, motorista, caminhao);

        if (operacao.equals("update")){
            return freteDAO.update(frete);
        }
        else if (operacao.equals("insert")){
            return freteDAO.insert(frete) >= 0;
        }

        return true;
    }

    public boolean delete(@NotNull String codFrete){
        Integer codFreteNumber = paramConverter.convertStringToInt(codFrete);

        if (codFreteNumber == null || codFreteNumber <= 0){
            return false;
        }
        else{
            if (freteDAO.selectUnique(codFreteNumber) == null){
                throw new IllegalArgumentException("Motorista não encontrado");
            }
            else{
                    return freteDAO.delete(codFreteNumber);
            }
        }
    }


    private boolean validarCampos(String operacao, String origem, String destino, String valor_tonelada,
                                  String peso, String observacao, String estado, String cod_Motorista, String cod_Caminhao){
        if (!(operacao.equals("insert") || operacao.equals("update"))) {
            return false;
        }
        else if (origem.isBlank() || origem.length() > 50 || destino.isBlank() || destino.length() > 50 || observacao.length() > 1024
                || estado.length() > 20 || estado.isBlank() || cod_Motorista.isBlank() || cod_Caminhao.isBlank() ||
                valor_tonelada.isBlank() || peso.isBlank()){

            return false;
        }
        else return estado.equals("PENDENTE") || estado.equals("CONCLUIDO");
    }
}
