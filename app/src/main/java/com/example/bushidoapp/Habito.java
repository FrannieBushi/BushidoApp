package com.example.bushidoapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Habito{

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;
    private String tipo;
    private String duracion;

    public Habito(String nombre, String tipo, String duracion) {

        this.nombre = nombre;
        this.tipo = tipo;
        this.duracion = duracion;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

}
