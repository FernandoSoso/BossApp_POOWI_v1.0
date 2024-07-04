package br.csi.service;

import br.csi.dao.CaminhaoDAO;
import br.csi.dao.FreteDAO;
import br.csi.dao.MotoristaDAO;
import br.csi.model.Caminhao;
import br.csi.model.Frete;
import br.csi.model.Motorista;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FreteService {
    private final FreteDAO freteDAO = new FreteDAO();
    private final MotoristaDAO motoristaDAO = new MotoristaDAO();
    private final CaminhaoDAO caminhaoDAO = new CaminhaoDAO();

    public ArrayList<Frete> selectAll(String offset) {
        if (offset == null){
            return freteDAO.selectAll(0);
        }
        else if (offset.isBlank()){
            return freteDAO.selectAll(0);
        }
        else{
            int numOffset = Integer.parseInt(offset);
            return freteDAO.selectAll(numOffset);
        }
    }

    public Frete selectUnique(@NotNull String cod) {
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

        Frete frete = freteDAO.selectUnique(codNumero);

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

    public boolean persist(@NotNull String operacao, String cod,@NotNull  String origem, String origem_data,@NotNull  String destino, String destino_data, double valor_tonelada, double peso, String observacao,@NotNull  String estado, int cod_Motorista, int cod_Caminhao) {
        int numCod;
        Date origem_data_date = null;
        Date destino_data_date = null;

        if (!validarCampos(operacao, origem, destino, valor_tonelada, peso, observacao, estado, cod_Motorista, cod_Caminhao)){
            return false;
        }

        if (origem_data != null){
            if (!origem_data.isBlank()){
                try {
                    origem_data_date = new SimpleDateFormat("dd/MM/yyyy").parse(origem_data);
                } catch (Exception e) {
                    return false;
                }
            }
        }

        if (destino_data != null){
            if (!destino_data.isBlank()){
                try {
                    destino_data_date = new SimpleDateFormat("dd/MM/yyyy").parse(destino_data);
                } catch (Exception e) {
                    return false;
                }
            }
        }

        if (observacao.isBlank()){
            observacao = null;
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
        Caminhao caminhao = new Caminhao(cod_Caminhao, null, null, null, 0, 0, 0, null);
        Motorista motorista = new Motorista(cod_Motorista, null, null, null, null, null);
        Frete frete = new Frete(numCod, origem, origem_data_date, destino, destino_data_date, valor_tonelada, peso, observacao, estado, motorista, caminhao);

        if (operacao.equals("update")){
            return freteDAO.update(frete);
        }
        else if (operacao.equals("insert")){
            int codFrete = freteDAO.insert(frete);

            return codFrete >= 0;
        }

        return true;
    }

    public boolean delete(@NotNull String cod){
        if (cod.isBlank()){
            return false;
        }
        else{
            int codNumero = Integer.parseInt(cod);
            if (codNumero <= 0){
                return false;
            }
            else {
                if (freteDAO.selectUnique(codNumero) == null){
                    throw new IllegalArgumentException("Motorista não encontrado");
                }
                else{
                        return freteDAO.delete(codNumero);
                }
            }
        }
    }


    private boolean validarCampos(String operacao, String origem, String destino, double valor_tonelada, double peso, String observacao, String estado, int cod_Motorista, int cod_Caminhao){
        if (!(operacao.equals("insert") || operacao.equals("update"))) {
            return false;
        }
        else if (origem.isBlank() || origem.length() > 50 || destino.isBlank() || destino.length() > 50 || observacao.length() > 1024 || estado.length() > 1 || estado.isBlank()){
            return false;
        }
        else if (peso <= 0 || valor_tonelada <= 0 || cod_Motorista <= 0 || cod_Caminhao <= 0){
            return false;
        }

        return true;
    }
}
