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
public class Cliente {
    private int id;
    private String numeroUni;
    private String correo;
    private int estatus;
    Persona persona; //Solamente objeto debido a que se asocia solamente a un elemento, y no a varios
    Usuario usuario;

    public Cliente() {}

    public Cliente(String numeroUni, String correo, int estatus, Persona persona, Usuario usuario) {
        this.numeroUni = numeroUni;
        this.correo = correo;
        this.estatus = estatus;
        this.persona = persona;
        this.usuario = usuario;
    }

    public Cliente(int id, String numeroUni, String correo, int estatus, Persona persona, Usuario usuario) {
        this.id = id;
        this.numeroUni = numeroUni;
        this.correo = correo;
        this.estatus = estatus;
        this.persona = persona;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroUni() {
        return numeroUni;
    }

    public void setNumeroUni(String numeroUni) {
        this.numeroUni = numeroUni;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
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
        return "Cliente{" + "id=" + id + ", numeroUni=" + numeroUni + ", correo=" + correo + ", estatus=" + estatus + ", persona=" + persona.toString() + ", usuario=" + usuario.toString() + '}';
    }
    
}
