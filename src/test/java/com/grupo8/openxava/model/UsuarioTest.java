package com.grupo8.openxava.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class UsuarioTest {

    private Usuario crearUsuarioCompleto() {
        Departamento departamento = new Departamento();
        departamento.setNombre("Sistemas");

        Empleado empleado = new Empleado();
        empleado.setCedula("1234567890");
        empleado.setNombre("Juan Perez");
        empleado.setCargo("Analista");
        empleado.setActivo(true);
        empleado.setDepartamento(departamento);

        Usuario usuario = new Usuario();
        usuario.setUsername("jperez");
        usuario.setPasswordHash("secreto123");
        usuario.setRol("EMPLEADO");
        usuario.setEmpleado(empleado);

        return usuario;
    }

    @Test
    public void debeCrearUsuario() {
        Usuario usuario = crearUsuarioCompleto();

        assertEquals("jperez", usuario.getUsername());
        assertEquals("secreto123", usuario.getPasswordHash());
        assertEquals("EMPLEADO", usuario.getRol());
    }

    @Test
    public void debeAsociarEmpleado() {
        Usuario usuario = crearUsuarioCompleto();

        assertNotNull(usuario.getEmpleado());
        assertEquals("1234567890", usuario.getEmpleado().getCedula());
        assertEquals("Juan Perez", usuario.getEmpleado().getNombre());
    }

    @Test
    public void debeCambiarRolAAdmin() {
        Usuario usuario = crearUsuarioCompleto();
        usuario.setRol("ADMIN");

        assertEquals("ADMIN", usuario.getRol());
    }

    @Test
    public void debeCambiarRolASupervisor() {
        Usuario usuario = crearUsuarioCompleto();
        usuario.setRol("SUPERVISOR");

        assertEquals("SUPERVISOR", usuario.getRol());
    }

    @Test
    public void debeActualizarUsername() {
        Usuario usuario = crearUsuarioCompleto();
        usuario.setUsername("jcarlosperez");

        assertEquals("jcarlosperez", usuario.getUsername());
    }

    @Test
    public void idDebeSerNuloAntesDePersistir() {
        Usuario usuario = crearUsuarioCompleto();

        assertNull(usuario.getId());
    }
}
