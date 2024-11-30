package com.teos.school.school_management.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombres;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellidos;

    @NotBlank(message = "La matrícula no puede estar vacía")
    private String matricula;

    @NotNull(message = "El promedio no puede ser nulo")
    @DecimalMin(value = "0.0", message = "El promedio no puede ser negativo")
    private Double promedio;

    private String fotoPerfilUrl;  

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Double getPromedio() {
        return promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public String toEmailString() {
        return "Alumno: " + nombres + " " + apellidos + "\n" +
            "Matrícula: " + matricula + "\n" +
            "Promedio: " + promedio + "\n";
    }
}
