/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utl.dsm.myspa.model;

import java.util.List;

/**
 *
 * @author marti
 */
public class ServicioT {
    private int id;
    private Tratamiento tratamiento;
    private List<Producto> productos;

    public ServicioT() {}

    public ServicioT(Tratamiento tratamiento, List<Producto> productos) {
        this.tratamiento = tratamiento;
        this.productos = productos;
    }

    public ServicioT(int id, Tratamiento tratamiento, List<Producto> productos) {
        this.id = id;
        this.tratamiento = tratamiento;
        this.productos = productos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return "ServicioT{" + "id=" + id + ", tratamiento=" + tratamiento + ", productos=" + productos + '}';
    }
}
