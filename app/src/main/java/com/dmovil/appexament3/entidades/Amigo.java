package com.dmovil.appexament3.entidades;

import java.io.Serializable;

public class Amigo implements Serializable {

    //Se declara los atributos de la clase Amigo
    private int icono;
    private String idAmigo;
    private String nombre;
    private String telefono;
    private String email;
    private String direccion;

    //Se crea un constructor vacío
    public Amigo() {
    }

    //Se crea un constructor parametrizado
    public Amigo(int icono, String idAmigo, String nombre, String telefono, String email, String direccion) {
        this.icono = icono;
        this.idAmigo = idAmigo;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
    }

    //Se crean todos los metodos getter y setter para cada atributo de la clase

    public String getIdAmigo() {
        return idAmigo;
    }

    public void setIdAmigo(String idAmigo) {
        this.idAmigo = idAmigo;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
