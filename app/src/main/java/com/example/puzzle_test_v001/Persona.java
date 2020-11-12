package com.example.puzzle_test_v001;

public class Persona {
    private String nom;
    private int puntuacio;


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPuntuacio() {
        return puntuacio;
    }

    public void setPuntuacio(int puntuacio) {
        this.puntuacio = puntuacio;
    }


    @Override
    public String toString() {
        return nom + "  -  " + puntuacio;
    }
}
