package br.csi.service;

import br.csi.dao.CaminhaoDAO;
import br.csi.dao.DespesaDAO;
import br.csi.dao.FreteDAO;
import br.csi.dao.MotoristaDAO;
import br.csi.model.Caminhao;
import br.csi.model.Frete;
import br.csi.model.Motorista;
import br.csi.util.ParamConverter;
import br.csi.util.Round;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class FreteService {
    private final FreteDAO freteDAO = new FreteDAO();
    private final MotoristaDAO motoristaDAO = new MotoristaDAO();
    private final CaminhaoDAO caminhaoDAO = new CaminhaoDAO();
    private final ParamConverter paramConverter = new ParamConverter();

    public ArrayList<Frete> selectAll(String offset, String limit) {
        Integer offsetNumber = paramConverter.convertStringToInt(offset);
        Integer limitNumber = paramConverter.convertStringToInt(limit);

        offsetNumber = offsetNumber == null ? 0 : offsetNumber;
        limitNumber = limitNumber == null ? 0 : limitNumber;

        ArrayList<Frete> fretes = freteDAO.selectAll(offsetNumber, limitNumber);
        fretes.forEach(frete -> {
            frete.setValorLiquido(frete.getValorBruto());
            frete.setValorBruto();
        });

        return fretes;
    }

    public Frete selectUnique(@NotNull String codFrete) {
        Integer codFreteNumber = paramConverter.convertStringToInt(codFrete);

        if (codFreteNumber == null || codFreteNumber <= 0){
            return null;
        }

        Frete frete = freteDAO.selectUnique(codFreteNumber);

        Motorista motorista = motoristaDAO.selectUnique(frete.getMotorista().getCod());
        frete.setMotorista(motorista);

        Caminhao caminhao = caminhaoDAO.selectUnique(frete.getCaminhao().getCod());
        frete.setCaminhao(caminhao);

        frete.setValorBruto();

        frete.setParteMotorista(frete.getValorBruto() * new CaminhaoDAO().selectUnique(frete.getCaminhao().getCod()).getPercentualMotorista());

        return frete;
    }

    public boolean persist(@NotNull String operacao, String codFrete, @NotNull String origem, String origem_data, @NotNull String destino,
                           String destino_data, @NotNull String valorTonelada, @NotNull String peso, String observacao, @NotNull  String estado,
                           @NotNull String codMotorista, @NotNull String codCaminhao) {

        if (!validarCampos(operacao, origem, destino, valorTonelada, peso, observacao, estado, codMotorista, codCaminhao)){
            System.out.println("Erro: Campos inválidos!");
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
            Frete freteAntigo = freteDAO.selectUnique(frete.getCod());
            if (freteAntigo == null){
                throw new IllegalArgumentException("Frete não encontrado");
            }

            if (!Objects.equals(freteAntigo.getValorBruto(), frete.getValorBruto())){
                caminhao = caminhaoDAO.selectUnique(codCaminhaoNumber);
                double difValorBruto = frete.getValorBruto() - freteAntigo.getValorBruto();
                double newParteMotorista = Round.roundUp(frete.getValorBruto() * caminhao.getPercentualMotorista(), 2);
                double difParteMotorista = freteAntigo.getParteMotorista() - newParteMotorista;

                frete.setParteMotorista(newParteMotorista);
                frete.setValorLiquido(freteAntigo.getValorLiquido() + difValorBruto + difParteMotorista);
            }


            return freteDAO.update(frete);
        }
        else if (operacao.equals("insert")){
            caminhao = caminhaoDAO.selectUnique(codCaminhaoNumber);
            frete.setParteMotorista(Round.roundUp(frete.getValorBruto() * caminhao.getPercentualMotorista(), 2));
            frete.setValorLiquido(frete.getValorBruto() - frete.getParteMotorista());

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


    private boolean validarCampos(String operacao, String origem, String destino, String valorTonelada,
                                  String peso, String observacao, String estado, String codMotorista, String codCaminhao){
        if (!(operacao.equals("insert") || operacao.equals("update"))) {
            return false;
        }
        else if (origem.isBlank() || origem.length() > 35 || destino.isBlank() || destino.length() > 35 || observacao.length() > 256
                || estado.length() > 25 || estado.isBlank() || codMotorista.isBlank() || codCaminhao.isBlank() ||
                valorTonelada.isBlank() || peso.isBlank()){
            return false;
        }
        else return "pendente".equalsIgnoreCase(estado) || "concluído".equalsIgnoreCase(estado);
    }
}
