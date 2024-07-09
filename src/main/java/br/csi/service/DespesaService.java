package br.csi.service;

import br.csi.dao.DespesaDAO;
import br.csi.dao.FreteDAO;
import br.csi.model.Despesa;
import br.csi.util.ParamConverter;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.util.ArrayList;


public class DespesaService {
    private final DespesaDAO despesaDAO = new DespesaDAO();
    private final FreteDAO freteDAO = new FreteDAO();

    private final ParamConverter paramConverter = new ParamConverter();

    public ArrayList<Despesa> selectAll(@NotNull String offset, @NotNull String codFrete){
        Integer offsetNumber = paramConverter.convertStringToInt(offset);
        Integer codFreteNumber = paramConverter.convertStringToInt(codFrete);

        if (codFreteNumber == null){
            return null;
        }
        else if (codFreteNumber <= 0){
            return null;
        }

        return despesaDAO.selectAll(codFreteNumber, offsetNumber);
    }

    public boolean persist(@NotNull String operacao, String codDespesa, @NotNull String tipo, @NotNull String valor, String dataInsercao, @NotNull String codFrete) {
        if (!validarCampos(operacao, tipo, valor, dataInsercao)){
            return false;
        }

        Integer codDespesaNumber = paramConverter.convertStringToInt(codDespesa);
        Integer codFreteNumber = paramConverter.convertStringToInt(codFrete);
        Double valorNumber = paramConverter.convertStringToDouble(valor);
        Date dataInsercaoDate = paramConverter.convertStringToDate(dataInsercao);

        if (freteDAO.selectUnique(codFreteNumber) == null){
            return false;
        }

        Despesa despesa = new Despesa(codDespesaNumber, tipo, valorNumber, codFreteNumber, dataInsercaoDate);

        if (operacao.equals("insert")){
            return despesaDAO.insert(despesa) > 0;
        }
        else{
            return despesaDAO.update(despesa);
        }
    }

    public boolean delete(@NotNull String codDespesa){
        Integer codDespesaNumber = paramConverter.convertStringToInt(codDespesa);

        if (codDespesaNumber == null || codDespesaNumber <= 0){
            return false;
        }

        return despesaDAO.delete(codDespesaNumber);
    }

    private boolean validarCampos(String operacao, String tipo, String valor, String data){
        if (!(operacao.equals("update") || operacao.equals("insert"))){
            return false;
        }
        else if (tipo == null || valor == null || data == null){
            return false;
        }
        else return !(valor.isBlank() && data.isBlank() && tipo.isBlank()) && tipo.length() <= 50;
    }
}
