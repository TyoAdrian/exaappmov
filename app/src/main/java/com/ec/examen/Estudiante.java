package com.ec.examen;

public class Estudiante {
    private String nombre;
    private double promedio;

    public Estudiante() {
        // Constructor vacío necesario para Firebase
    }

    public Estudiante(String nombre, double promedio) {
        this.nombre = nombre;
        this.promedio = promedio;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPromedio() {
        return promedio;
    }
}

