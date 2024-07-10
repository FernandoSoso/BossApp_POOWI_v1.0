package br.csi.util;

import lombok.Getter;

@Getter
public class Retorno {
    public Retorno(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    private final boolean error;
    private final String message;

}
