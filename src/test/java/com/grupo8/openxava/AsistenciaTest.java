package com.grupo8.openxava;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AsistenciaTest {

    private String calcularEstado(int horaEntrada, int minutoEntrada, int horaSalida, int minutoSalida) {
        int entrada = horaEntrada * 60 + minutoEntrada;
        int salida = horaSalida * 60 + minutoSalida;

        int horaEntradaEsperada = 8 * 60;
        int horaSalidaEsperada = 17 * 60;
        int tolerancia = 15;

        if (salida < entrada) {
            return "INCONSISTENTE";
        }

        if (entrada > horaEntradaEsperada + tolerancia) {
            return "RETRASO";
        }

        if (salida < horaSalidaEsperada) {
            return "SALIDA_ANTICIPADA";
        }

        return "COMPLETA";
    }

    @Test
    public void debeMarcarAsistenciaCompleta() {
        assertEquals("COMPLETA", calcularEstado(8, 5, 17, 10));
    }

    @Test
    public void debeMarcarRetraso() {
        assertEquals("RETRASO", calcularEstado(8, 20, 17, 5));
    }

    @Test
    public void debeMarcarSalidaAnticipada() {
        assertEquals("SALIDA_ANTICIPADA", calcularEstado(8, 10, 16, 40));
    }

    @Test
    public void debeMarcarRegistroInconsistente() {
        assertEquals("INCONSISTENTE", calcularEstado(9, 0, 8, 30));
    }
}