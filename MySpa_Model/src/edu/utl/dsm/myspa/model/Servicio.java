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
public class Servicio {
    private int id;
    private String fecha;
    private Reservacion reservacion;
    private Empleado empleado;
    private List<ServicioT> serviciosT;
    private float Total;

    public Servicio() {}

    public Servicio(String fecha, Reservacion reservacion, Empleado empleado, List<ServicioT> serviciosT, float Total) {
        this.fecha = fecha;
        this.reservacion = reservacion;
        this.empleado = empleado;
        this.serviciosT = serviciosT;
        this.Total = Total;
    }

    public Servicio(int id, String fecha, Reservacion reservacion, Empleado empleado, List<ServicioT> serviciosT, float Total) {
        this.id = id;
        this.fecha = fecha;
        this.reservacion = reservacion;
        this.empleado = empleado;
        this.serviciosT = serviciosT;
        this.Total = Total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Reservacion getReservacion() {
        return reservacion;
    }

    public void setReservacion(Reservacion reservacion) {
        this.reservacion = reservacion;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<ServicioT> getServiciosT() {
        return serviciosT;
    }

    public void setServiciosT(List<ServicioT> serviciosT) {
        this.serviciosT = serviciosT;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float Total) {
        this.Total = Total;
    }

    @Override
    public String toString() {
        return "Servicio{" + "id=" + id + ", fecha=" + fecha + ", reservacion=" + reservacion + ", empleado=" + empleado + ", serviciosT=" + serviciosT + ", Total=" + Total + '}';
    }
}
