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
public class ServicioTP {
    private ServicioT servicioT;
    private List<Producto> productos; //Asociación múltiple, es decir, una lista de productos asociados

    public ServicioTP() {
    }

    public ServicioTP(ServicioT servicioT, List<Producto> productos) {
        this.servicioT = servicioT;
        this.productos = productos;
    }

    public ServicioT getServicioT() {
        return servicioT;
    }

    public void setServicioT(ServicioT servicioT) {
        this.servicioT = servicioT;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return "ServicioTP{" + "servicioT=" + servicioT + ", productos=" + productos + '}';
    }
}
