package com.grupo8.openxava.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class DepartamentoTest {

    @Test
    public void debeCrearDepartamento() {
        Departamento departamento = new Departamento();
        departamento.setNombre("Recursos Humanos");
        departamento.setDescripcion("Departamento administrativo");

        assertEquals("Recursos Humanos", departamento.getNombre());
        assertEquals("Departamento administrativo", departamento.getDescripcion());
    }

    @Test
    public void debeActualizarNombre() {
        Departamento departamento = new Departamento();
        departamento.setNombre("IT");
        departamento.setNombre("Tecnologia");

        assertEquals("Tecnologia", departamento.getNombre());
    }

    @Test
    public void debePermitirDescripcionNula() {
        Departamento departamento = new Departamento();
        departamento.setNombre("Finanzas");

        assertNull(departamento.getDescripcion());
    }

    @Test
    public void toStringDebeRetornarNombre() {
        Departamento departamento = new Departamento();
        departamento.setNombre("Sistemas");

        assertEquals("Sistemas", departamento.toString());
    }

    @Test
    public void idDebeSerNuloAntesDePersistir() {
        Departamento departamento = new Departamento();
        departamento.setNombre("Operaciones");

        assertNull(departamento.getId());
    }
}
