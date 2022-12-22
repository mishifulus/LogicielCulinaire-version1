/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utl.dsm.myspa.model;

/**
 *
 * @author marti
 */
public class Producto {
    private int id;
    private String nombre;
    private String marca;
    private int estatus;
    private float precioUso;

    public Producto() {}

    public Producto(String nombre, String marca, int estatus, float precioUso) {
        this.nombre = nombre;
        this.marca = marca;
        this.estatus = estatus;
        this.precioUso = precioUso;
    }

    public Producto(int id, String nombre, String marca, int estatus, float precioUso) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.estatus = estatus;
        this.precioUso = precioUso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public float getPrecioUso() {
        return precioUso;
    }

    public void setPrecioUso(float precioUso) {
        this.precioUso = precioUso;
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", nombre=" + nombre + ", marca=" + marca + ", estatus=" + estatus + ", precioUso=" + precioUso + '}';
    }
    
}
