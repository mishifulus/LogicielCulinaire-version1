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
public class Empleado {
    private int id;
    private String numEmpleado;
    private String puesto;
    private int estatus;
    private String foto;
    private String rutaFoto;
    private Persona persona;
    private Usuario usuario;

    public Empleado() {}

    public Empleado(String numEmpleado, String puesto, int estatus, String foto, String rutaFoto, Persona persona, Usuario usuario) {
        this.numEmpleado = numEmpleado;
        this.puesto = puesto;
        this.estatus = estatus;
        this.foto = foto;
        this.rutaFoto = rutaFoto;
        this.persona = persona;
        this.usuario = usuario;
    }

    public Empleado(int id, String numEmpleado, String puesto, int estatus, String foto, String rutaFoto, Persona persona, Usuario usuario) {
        this.id = id;
        this.numEmpleado = numEmpleado;
        this.puesto = puesto;
        this.estatus = estatus;
        this.foto = foto;
        this.rutaFoto = rutaFoto;
        this.persona = persona;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(String numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Empleado{" + "id=" + id + ", numEmpleado=" + numEmpleado + ", puesto=" + puesto + ", estatus=" + estatus + ", foto=" + foto + ", rutaFoto=" + rutaFoto + ", persona=" + persona.toString() + ", usuario=" + usuario.toString() + '}';
    }
    
    
    
}
