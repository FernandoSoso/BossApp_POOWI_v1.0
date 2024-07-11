package br.csi.util;

import lombok.Getter;

@Getter
public class Retorno {
    public Retorno(String message) {
        this.erro = message;
    }

    private final String erro;

}
