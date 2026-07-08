package com.grupo8.openxava.integration;

import static org.junit.Assert.*;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openxava.jpa.XPersistence;

import com.grupo8.openxava.model.Departamento;
import com.grupo8.openxava.model.Empleado;
import com.grupo8.openxava.model.Horario;
import com.grupo8.openxava.model.RegistroAsistencia;

/**
 * Pruebas de integración que usan HSQLDB en memoria (definida en
 * src/test/resources/META-INF/persistence.xml).
 *
 * Requisito previo para ejecutar:
 *   mvn test -Dtest=PersistenciaTest
 *
 * El persistence.xml de test usa jdbc:hsqldb:mem:testdb, por lo que
 * estas pruebas no modifican la base de datos de producción (data/sicae-db.*).
 */
public class PersistenciaTest {

    @Before
    public void abrirContexto() {
        XPersistence.reset();
    }

    @After
    public void cerrarContexto() {
        XPersistence.rollback();
        XPersistence.reset();
    }

    @Test
    public void debeGuardarDepartamento() {
        Departamento departamento = new Departamento();
        departamento.setNombre("Sistemas");
        departamento.setDescripcion("Area de tecnologia");

        XPersistence.getManager().persist(departamento);
        XPersistence.commit();

        assertNotNull(departamento.getId());
    }

    @Test
    public void debeGuardarHorarioAsociadoADepartamento() {
        Departamento departamento = new Departamento();
        departamento.setNombre("Contabilidad");

        XPersistence.getManager().persist(departamento);

        Horario horario = new Horario();
        horario.setHoraEntrada(Time.valueOf("08:00:00"));
        horario.setHoraSalida(Time.valueOf("17:00:00"));
        horario.setDepartamento(departamento);

        XPersistence.getManager().persist(horario);
        XPersistence.commit();

        assertNotNull(horario.getId());
        assertEquals("Contabilidad", horario.getDepartamento().getNombre());
    }

    @Test
    public void debeGuardarEmpleadoConDepartamentoYHorario() {
        Departamento departamento = new Departamento();
        departamento.setNombre("Ventas");
        XPersistence.getManager().persist(departamento);

        Horario horario = new Horario();
        horario.setHoraEntrada(Time.valueOf("09:00:00"));
        horario.setHoraSalida(Time.valueOf("18:00:00"));
        horario.setDepartamento(departamento);
        XPersistence.getManager().persist(horario);

        Empleado empleado = new Empleado();
        empleado.setCedula("2222222222");
        empleado.setNombre("Ana Torres");
        empleado.setCargo("Vendedora");
        empleado.setActivo(true);
        empleado.setDepartamento(departamento);
        empleado.setHorario(horario);
        XPersistence.getManager().persist(empleado);

        XPersistence.commit();

        assertNotNull(empleado.getId());
        assertEquals("Ventas", empleado.getDepartamento().getNombre());
    }

    @Test
    public void debeGuardarRegistroAsistenciaYCalcularEstado() {
        Departamento departamento = new Departamento();
        departamento.setNombre("RRHH");
        XPersistence.getManager().persist(departamento);

        Horario horario = new Horario();
        horario.setHoraEntrada(Time.valueOf("08:00:00"));
        horario.setHoraSalida(Time.valueOf("17:00:00"));
        horario.setDepartamento(departamento);
        XPersistence.getManager().persist(horario);

        Empleado empleado = new Empleado();
        empleado.setCedula("3333333333");
        empleado.setNombre("Luis Castro");
        empleado.setCargo("Asistente");
        empleado.setActivo(true);
        empleado.setDepartamento(departamento);
        empleado.setHorario(horario);
        XPersistence.getManager().persist(empleado);

        RegistroAsistencia registro = new RegistroAsistencia();
        registro.setEmpleado(empleado);
        registro.setFecha(new Date());
        registro.setHoraEntrada(Time.valueOf("08:00:00"));
        registro.setHoraSalida(Time.valueOf("17:00:00"));

        XPersistence.getManager().persist(registro);
        XPersistence.commit();

        assertNotNull(registro.getId());
        assertEquals("COMPLETO", registro.getEstado());
        assertFalse(registro.isEsRetraso());
    }

    @Test
    public void debeRecuperarRegistrosDeAsistenciaDesdeBase() {
        Departamento departamento = new Departamento();
        departamento.setNombre("Logistica");
        XPersistence.getManager().persist(departamento);

        Horario horario = new Horario();
        horario.setHoraEntrada(Time.valueOf("07:00:00"));
        horario.setHoraSalida(Time.valueOf("16:00:00"));
        horario.setDepartamento(departamento);
        XPersistence.getManager().persist(horario);

        Empleado empleado = new Empleado();
        empleado.setCedula("4444444444");
        empleado.setNombre("Pedro Mora");
        empleado.setCargo("Operador");
        empleado.setActivo(true);
        empleado.setDepartamento(departamento);
        empleado.setHorario(horario);
        XPersistence.getManager().persist(empleado);

        RegistroAsistencia registro = new RegistroAsistencia();
        registro.setEmpleado(empleado);
        registro.setFecha(new Date());
        registro.setHoraEntrada(Time.valueOf("07:00:00"));
        registro.setHoraSalida(Time.valueOf("16:00:00"));
        XPersistence.getManager().persist(registro);

        XPersistence.commit();

        List<RegistroAsistencia> resultados = XPersistence.getManager()
                .createQuery("from RegistroAsistencia r where r.empleado.cedula = :cedula",
                        RegistroAsistencia.class)
                .setParameter("cedula", "4444444444")
                .getResultList();

        assertFalse(resultados.isEmpty());
        assertEquals("COMPLETO", resultados.get(0).getEstado());
    }
}
