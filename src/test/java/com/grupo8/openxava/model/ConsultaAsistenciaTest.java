package com.grupo8.openxava.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

/**
 * Pruebas unitarias para ConsultaAsistencia.
 *
 * Los tests que llaman a getRegistros() con fechas no nulas requieren un
 * EntityManager activo (XPersistence) y se deben ejecutar como pruebas de
 * integración (ver PersistenciaTest). Aquí solo se prueban los casos de
 * fechas nulas, que retornan tempranamente sin tocar la base de datos.
 */
public class ConsultaAsistenciaTest {

    @Test
    public void debeRetornarListaVaciaSiFechaInicionEsNula() {
        ConsultaAsistencia consulta = new ConsultaAsistencia();
        consulta.setFechaInicio(null);
        consulta.setFechaFin(new Date());

        assertTrue(consulta.getRegistros().isEmpty());
    }

    @Test
    public void debeRetornarListaVaciaSiFechaFinEsNula() {
        ConsultaAsistencia consulta = new ConsultaAsistencia();
        consulta.setFechaInicio(new Date());
        consulta.setFechaFin(null);

        assertTrue(consulta.getRegistros().isEmpty());
    }

    @Test
    public void debeRetornarListaVaciaSiAmbasFechasSonNulas() {
        ConsultaAsistencia consulta = new ConsultaAsistencia();
        consulta.setFechaInicio(null);
        consulta.setFechaFin(null);

        assertTrue(consulta.getRegistros().isEmpty());
    }

    @Test
    public void debePermitirAsignarEmpleadoOpcional() {
        Departamento departamento = new Departamento();
        departamento.setNombre("Sistemas");

        Empleado empleado = new Empleado();
        empleado.setCedula("1234567890");
        empleado.setNombre("Juan Perez");
        empleado.setCargo("Analista");
        empleado.setDepartamento(departamento);

        ConsultaAsistencia consulta = new ConsultaAsistencia();
        consulta.setEmpleado(empleado);

        assertEquals("1234567890", consulta.getEmpleado().getCedula());
    }

    @Test
    public void debeRetornarListaVaciaSinEmpleadoYFechasNulas() {
        ConsultaAsistencia consulta = new ConsultaAsistencia();
        // sin empleado, sin fechas

        assertTrue(consulta.getRegistros().isEmpty());
    }
}
