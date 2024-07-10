package br.csi.util;

import java.text.SimpleDateFormat;
import java.sql.Date;

/**
 * Essa classe possui funções úteis para converter os parâmetros de requisição para os tipos corretos.
 */
public class ParamConverter {

    /**
     * Converte uma string para um inteiro.
     * @param value String a ser convertida.
     * @return Inteiro convertido.
     */
    public Integer convertStringToInt(String value){
        if (value == null){
            return 0;
        }
        else if (value.isBlank()){
            return 0;
        }
        else {
            return Integer.parseInt(value.trim());
        }
    }

    /**
     * Converte uma string para um double.
     * @param value String a ser convertida.
     * @return Double convertido.
     */
    public Double convertStringToDouble(String value){
        if (value == null){
            return 0.0;
        }
        else if (value.isBlank()){
            return 0.0;
        }
        else {
            return Double.parseDouble(value.trim());
        }
    }

    /**
     * Converte uma string para um boolean.
     * @param value String a ser convertida.
     * @return Boolean convertido.
     */
    public String convertBlankStringToNull(String value){
        if (value == null){
            return null;
        }
        else if (value.isBlank()){
            return null;
        }
        else {
            return value.trim();
        }
    }

    /**
     * Converte uma string para uma data.
     * @param value String a ser convertida.
     * @return Data convertida.
     */
    public Date convertStringToDate(String value){
        if (value == null){
            return null;
        }
        else if (value.isBlank()){
            return null;
        }
        else {
            try {
                return new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(value.trim()).getTime());
            } catch (Exception e) {
                return null;
            }
        }
    }
}
