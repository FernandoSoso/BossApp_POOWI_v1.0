package br.csi.service;

import br.csi.dao.DespesaDAO;
import br.csi.dao.FreteDAO;
import br.csi.model.Despesa;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class DespesaService {
    private final DespesaDAO despesaDAO = new DespesaDAO();
    private final FreteDAO freteDAO = new FreteDAO();

    public ArrayList<Despesa> selectAll(@NotNull String offset, @NotNull String codFrete){
        int numOffset;
        int numCodFrete;

        if (offset.isBlank()){
            numOffset = 1;
        }
        else{
            numOffset = Integer.parseInt(offset);
        }

        if (codFrete.isBlank()){
            return null;
        }
        else{
            numCodFrete = Integer.parseInt(codFrete);
        }

        return despesaDAO.selectAll(numCodFrete, numOffset);
    }

    public boolean persist(@NotNull String operacao, String cod, String tipo, String valor, String data_insercao, String codFrete) {
        int numCod;
        int numCodFrete;
        double numValor;
        Date data_insercao_date = null;

        if (!validarCampos(operacao, tipo, valor, data_insercao)){
            return false;
        }

        if (cod == null){
            numCod = -1;
        }
        else if (cod.isBlank()){
            numCod = -1;
        }
        else{
            numCod = Integer.parseInt(cod);
        }

        if (codFrete == null){
            numCodFrete = -1;
        }
        else if (codFrete.isBlank()){
            numCodFrete = -1;
        }
        else{
            numCodFrete = Integer.parseInt(codFrete);

            if (freteDAO.selectUnique(numCodFrete) == null){
                return false;
            }
        }

        if (valor == null){
            return false;
        }
        else if (valor.isBlank()){
            return false;
        }
        else{
            numValor = Double.parseDouble(valor);
        }

        if (data_insercao != null){
            if (!data_insercao.isBlank()){
                try {
                    data_insercao_date = new Date(new SimpleDateFormat("dd/MM/yyyy").parse(data_insercao).getTime());
                } catch (Exception e) {
                    return false;
                }
            }
        }

        Despesa despesa = new Despesa(numCod, tipo, numValor, numCodFrete, data_insercao_date);

        if (operacao.equals("insert")){
            return despesaDAO.insert(despesa) > 0;
        }
        else{
            return despesaDAO.update(despesa);
        }
    }

    public boolean delete(@NotNull String cod){
        if (cod.isBlank()){
            return false;
        }

        return despesaDAO.delete(Integer.parseInt(cod));
    }

    private boolean validarCampos(String operacao, String tipo, String valor, String data){
        if (!(operacao.equals("update") || operacao.equals("insert"))){
            return false;
        }
        else if (tipo == null || valor == null || data == null){
            return false;
        }
        else if (valor.isBlank() || data.isBlank() || tipo.isBlank() || tipo.length() > 50){
            return false;
        }

        return true;
    }
}
