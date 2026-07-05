package com.grupo8.openxava.model;

import static org.junit.Assert.*;

import java.sql.Time;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class RegistroAsistenciaTest {

    private Departamento departamento;
    private Horario horario;
    private Empleado empleado;

    @Before
    public void setUp() {
        departamento = new Departamento();
        departamento.setNombre("Sistemas");

        horario = new Horario();
        horario.setDepartamento(departamento);
        horario.setHoraEntrada(Time.valueOf("08:00:00"));
        horario.setHoraSalida(Time.valueOf("17:00:00"));

        empleado = new Empleado();
        empleado.setCedula("1234567890");
        empleado.setNombre("Juan Perez");
        empleado.setCargo("Analista");
        empleado.setActivo(true);
        empleado.setDepartamento(departamento);
        empleado.setHorario(horario);
    }

    private RegistroAsistencia crearRegistro(String entrada, String salida) {
        RegistroAsistencia registro = new RegistroAsistencia();
        registro.setEmpleado(empleado);
        registro.setFecha(new Date());
        registro.setHoraEntrada(Time.valueOf(entrada));
        if (salida != null) {
            registro.setHoraSalida(Time.valueOf(salida));
        }
        return registro;
    }

    @Test
    public void debeMarcarCompleto() {
        RegistroAsistencia registro = crearRegistro("08:00:00", "17:00:00");
        registro.calcularEstadoAsistencia();
        assertEquals("COMPLETO", registro.getEstado());
        assertFalse(registro.isEsRetraso());
    }

    @Test
    public void debeMarcarCompletoConEntradaEnLimiteDeToleranacia() {
        // 08:15 no es "después" de 08:15, por lo que no hay retraso
        RegistroAsistencia registro = crearRegistro("08:15:00", "17:00:00");
        registro.calcularEstadoAsistencia();
        assertEquals("COMPLETO", registro.getEstado());
        assertFalse(registro.isEsRetraso());
    }

    @Test
    public void debeMarcarCompletoConRetraso() {
        // 08:16 supera el límite de tolerancia de 08:15
        RegistroAsistencia registro = crearRegistro("08:16:00", "17:00:00");
        registro.calcularEstadoAsistencia();
        assertEquals("COMPLETO CON RETRASO", registro.getEstado());
        assertTrue(registro.isEsRetraso());
    }

    @Test
    public void debeMarcarEnCursoSinSalida() {
        RegistroAsistencia registro = crearRegistro("08:00:00", null);
        registro.calcularEstadoAsistencia();
        assertEquals("EN CURSO", registro.getEstado());
        assertFalse(registro.isEsRetraso());
    }

    @Test
    public void debeMarcarEnCursoConRetrasoSinSalida() {
        RegistroAsistencia registro = crearRegistro("08:30:00", null);
        registro.calcularEstadoAsistencia();
        assertEquals("EN CURSO CON RETRASO", registro.getEstado());
        assertTrue(registro.isEsRetraso());
    }

    @Test
    public void debeMarcarSalidaAnticipada() {
        RegistroAsistencia registro = crearRegistro("08:00:00", "16:00:00");
        registro.calcularEstadoAsistencia();
        assertEquals("SALIDA ANTICIPADA", registro.getEstado());
        assertFalse(registro.isEsRetraso());
    }

    @Test
    public void debeMarcarSalidaAnticipadaConRetraso() {
        RegistroAsistencia registro = crearRegistro("08:30:00", "16:00:00");
        registro.calcularEstadoAsistencia();
        assertEquals("SALIDA ANTICIPADA CON RETRASO", registro.getEstado());
        assertTrue(registro.isEsRetraso());
    }

    @Test
    public void debeMarcarInconsistenteSiSalidaAntesDeEntrada() {
        RegistroAsistencia registro = crearRegistro("09:00:00", "08:30:00");
        registro.calcularEstadoAsistencia();
        assertEquals("INCONSISTENTE", registro.getEstado());
        assertFalse(registro.isEsRetraso());
    }

    @Test
    public void debeMarcarIncompletoSinEmpleado() {
        RegistroAsistencia registro = new RegistroAsistencia();
        registro.setFecha(new Date());
        registro.setHoraEntrada(Time.valueOf("08:00:00"));
        registro.setHoraSalida(Time.valueOf("17:00:00"));
        // empleado no asignado
        registro.calcularEstadoAsistencia();
        assertEquals("INCOMPLETO", registro.getEstado());
        assertFalse(registro.isEsRetraso());
    }

    @Test
    public void debeMarcarIncompletoSiEmpleadoSinHorario() {
        Empleado empleadoSinHorario = new Empleado();
        empleadoSinHorario.setCedula("0987654321");
        empleadoSinHorario.setNombre("Maria Lopez");
        empleadoSinHorario.setCargo("Contadora");
        empleadoSinHorario.setDepartamento(departamento);
        // horario no asignado

        RegistroAsistencia registro = new RegistroAsistencia();
        registro.setEmpleado(empleadoSinHorario);
        registro.setFecha(new Date());
        registro.setHoraEntrada(Time.valueOf("08:00:00"));
        registro.setHoraSalida(Time.valueOf("17:00:00"));
        registro.calcularEstadoAsistencia();
        assertEquals("INCOMPLETO", registro.getEstado());
    }

    @Test
    public void debeMarcarHorarioIncompletoSiHorarioSinHoras() {
        Horario horarioSinHoras = new Horario();
        horarioSinHoras.setDepartamento(departamento);
        // horaEntrada y horaSalida son null

        Empleado empleadoConHorarioIncompleto = new Empleado();
        empleadoConHorarioIncompleto.setCedula("1111111111");
        empleadoConHorarioIncompleto.setNombre("Carlos Ruiz");
        empleadoConHorarioIncompleto.setCargo("Tecnico");
        empleadoConHorarioIncompleto.setDepartamento(departamento);
        empleadoConHorarioIncompleto.setHorario(horarioSinHoras);

        RegistroAsistencia registro = new RegistroAsistencia();
        registro.setEmpleado(empleadoConHorarioIncompleto);
        registro.setFecha(new Date());
        registro.setHoraEntrada(Time.valueOf("08:00:00"));
        registro.setHoraSalida(Time.valueOf("17:00:00"));
        registro.calcularEstadoAsistencia();
        assertEquals("HORARIO INCOMPLETO", registro.getEstado());
    }
}
