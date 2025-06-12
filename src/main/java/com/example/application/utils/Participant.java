package com.example.application.utils;

public class Participant {
    private int numero;
    private String nombre;

    public Participant() {
    }

    public Participant(int numero, String nombre) {
        this.numero = numero;
        this.nombre = nombre;
    }

    public int getNumero() {
        return numero;
    }

    public String getNombre() {
        return nombre;
    }
}
