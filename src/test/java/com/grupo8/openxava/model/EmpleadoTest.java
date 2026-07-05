package com.grupo8.openxava.model;

import static org.junit.Assert.*;

import java.sql.Time;

import org.junit.Test;

public class EmpleadoTest {

    private Empleado crearEmpleadoCompleto() {
        Departamento departamento = new Departamento();
        departamento.setNombre("Sistemas");

        Horario horario = new Horario();
        horario.setHoraEntrada(Time.valueOf("08:00:00"));
        horario.setHoraSalida(Time.valueOf("17:00:00"));
        horario.setDepartamento(departamento);

        Empleado empleado = new Empleado();
        empleado.setCedula("1234567890");
        empleado.setNombre("Juan Perez");
        empleado.setCargo("Analista");
        empleado.setActivo(true);
        empleado.setDepartamento(departamento);
        empleado.setHorario(horario);

        return empleado;
    }

    @Test
    public void debeCrearEmpleado() {
        Empleado empleado = crearEmpleadoCompleto();

        assertEquals("1234567890", empleado.getCedula());
        assertEquals("Juan Perez", empleado.getNombre());
        assertEquals("Analista", empleado.getCargo());
        assertTrue(empleado.isActivo());
    }

    @Test
    public void debeAsociarDepartamento() {
        Empleado empleado = crearEmpleadoCompleto();

        assertNotNull(empleado.getDepartamento());
        assertEquals("Sistemas", empleado.getDepartamento().getNombre());
    }

    @Test
    public void debeAsociarHorario() {
        Empleado empleado = crearEmpleadoCompleto();

        assertNotNull(empleado.getHorario());
        assertEquals(Time.valueOf("08:00:00"), empleado.getHorario().getHoraEntrada());
        assertEquals(Time.valueOf("17:00:00"), empleado.getHorario().getHoraSalida());
    }

    @Test
    public void debeCambiarEstadoActivoAFalse() {
        Empleado empleado = crearEmpleadoCompleto();
        assertTrue(empleado.isActivo());

        empleado.setActivo(false);

        assertFalse(empleado.isActivo());
    }

    @Test
    public void debeActualizarNombre() {
        Empleado empleado = crearEmpleadoCompleto();
        empleado.setNombre("Juan Carlos Perez");

        assertEquals("Juan Carlos Perez", empleado.getNombre());
    }

    @Test
    public void idDebeSerNuloAntesDePersistir() {
        Empleado empleado = crearEmpleadoCompleto();

        assertNull(empleado.getId());
    }
}
