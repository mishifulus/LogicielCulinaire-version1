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
public class Reservacion {
    private int id;
    private String fecha;
    private int estatus;
    private Sala sala;
    private Cliente cliente;
    private Horario horario;

    public Reservacion() {}

    public Reservacion(String fecha, int estatus, Sala sala, Cliente cliente, Horario horario) {
        this.fecha = fecha;
        this.estatus = estatus;
        this.sala = sala;
        this.cliente = cliente;
        this.horario = horario;
    }

    public Reservacion(int id, String fecha, int estatus, Sala sala, Cliente cliente, Horario horario) {
        this.id = id;
        this.fecha = fecha;
        this.estatus = estatus;
        this.sala = sala;
        this.cliente = cliente;
        this.horario = horario;
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

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    @Override
    public String toString() {
        return "Reservacion{" + "id=" + id + ", fecha=" + fecha + ", estatus=" + estatus + ", sala=" + sala + ", cliente=" + cliente + ", horario=" + horario + '}';
    }
}
