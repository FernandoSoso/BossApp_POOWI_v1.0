package br.csi.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Round {
    public static double roundUp(double valor, int casas) {
        BigDecimal bd = new BigDecimal(valor);
        bd = bd.stripTrailingZeros();

        if (bd.scale() > casas) {
            bd = bd.setScale(casas, RoundingMode.HALF_UP);
        }

        return bd.doubleValue();
    }
}
