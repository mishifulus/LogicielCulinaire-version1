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
public class Sucursal {
    private int id;
    private String nombre;
    private String domicilio;
    private double latitud;
    private double longitud;
    private int estatus;

    public Sucursal() {}

    public Sucursal(String nombre, String domicilio, double latitud, double longitud, int estatus) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estatus = estatus;
    }

    public Sucursal(int id, String nombre, String domicilio, double latitud, double longitud, int estatus) {
        this.id = id;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estatus = estatus;
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

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return "Sucursal{" + "id=" + id + ", nombre=" + nombre + ", domicilio=" + domicilio + ", latitud=" + latitud + ", longitud=" + longitud + ", estatus=" + estatus + '}';
    }
    
    
}
