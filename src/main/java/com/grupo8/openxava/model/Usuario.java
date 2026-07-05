package com.grupo8.openxava.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.openxava.annotations.DescriptionsList;
import org.openxava.annotations.Required;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 4, max = 50)
    @Required
    @Column(length = 50, unique = true)
    private String username;

    @NotBlank
    @Size(min = 6, max = 255)
    @Required
    @Column(length = 255)
    private String passwordHash;

    @NotBlank
    @Pattern(regexp = "ADMIN|EMPLEADO|SUPERVISOR")
    @Required
    @Column(length = 30)
    private String rol;

    @OneToOne
    @Required
    @DescriptionsList(descriptionProperties = "cedula, nombre")
    private Empleado empleado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
}
