package com.grupo8.openxava.model;

import static org.junit.Assert.*;

import java.sql.Time;

import org.junit.Test;

public class HorarioTest {

    @Test
    public void debeCrearHorario() {
        Horario horario = new Horario();
        horario.setHoraEntrada(Time.valueOf("08:00:00"));
        horario.setHoraSalida(Time.valueOf("17:00:00"));

        assertEquals(Time.valueOf("08:00:00"), horario.getHoraEntrada());
        assertEquals(Time.valueOf("17:00:00"), horario.getHoraSalida());
    }

    @Test
    public void debeAsociarDepartamento() {
        Departamento departamento = new Departamento();
        departamento.setNombre("Sistemas");

        Horario horario = new Horario();
        horario.setDepartamento(departamento);

        assertEquals("Sistemas", horario.getDepartamento().getNombre());
    }

    @Test
    public void esHorarioValidoSiSalidaEsMayorQueEntrada() {
        Horario horario = new Horario();
        horario.setHoraEntrada(Time.valueOf("08:00:00"));
        horario.setHoraSalida(Time.valueOf("17:00:00"));

        assertTrue(horario.esHorarioValido());
    }

    @Test
    public void noEsHorarioValidoSiSalidaEsMenorQueEntrada() {
        Horario horario = new Horario();
        horario.setHoraEntrada(Time.valueOf("17:00:00"));
        horario.setHoraSalida(Time.valueOf("08:00:00"));

        assertFalse(horario.esHorarioValido());
    }

    @Test
    public void noEsHorarioValidoSiHoraEntradaEsNula() {
        Horario horario = new Horario();
        horario.setHoraSalida(Time.valueOf("17:00:00"));

        assertFalse(horario.esHorarioValido());
    }

    @Test
    public void noEsHorarioValidoSiHoraSalidaEsNula() {
        Horario horario = new Horario();
        horario.setHoraEntrada(Time.valueOf("08:00:00"));

        assertFalse(horario.esHorarioValido());
    }

    @Test
    public void noEsHorarioValidoSiAmbasHorasSonNulas() {
        Horario horario = new Horario();

        assertFalse(horario.esHorarioValido());
    }
}
