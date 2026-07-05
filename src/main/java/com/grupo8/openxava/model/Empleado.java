package com.grupo8.openxava.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.openxava.annotations.DescriptionsList;
import org.openxava.annotations.Required;
import org.openxava.annotations.Tab;

@Entity
@Tab(
        properties = "cedula, nombre, cargo, activo, departamento.nombre, horario.horaEntrada, horario.horaSalida",
        defaultOrder = "${nombre} asc"
)
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 10, max = 10)
    @Required
    @Column(length = 20, unique = true)
    private String cedula;

    @NotBlank
    @Size(min = 3, max = 100)
    @Required
    @Column(length = 100)
    private String nombre;

    @NotBlank
    @Size(min = 3, max = 100)
    @Required
    @Column(length = 100)
    private String cargo;

    private boolean activo;

    @ManyToOne
    @Required
    @DescriptionsList(descriptionProperties = "nombre")
    private Departamento departamento;

    @ManyToOne
    @Required
    @DescriptionsList(descriptionProperties = "horaEntrada, horaSalida")
    private Horario horario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }
}