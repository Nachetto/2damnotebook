package org.example.common;

import java.time.Month;

public class NachoVerificacion{
    public static void verificar(int dia, Month mes, int anyo) throws FechaException {
        if (dia < 1|| dia > 30|| mes.getValue() <1 || anyo <2023 ||anyo>2050)
            throw new FechaException("La fecha introducida no es valida");
    }
}
