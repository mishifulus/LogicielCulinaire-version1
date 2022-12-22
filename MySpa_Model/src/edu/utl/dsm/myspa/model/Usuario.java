/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utl.dsm.myspa.model;

import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author marti
 */
public class Usuario {
    private int id;
    private String nombreUsu;
    private String contrasenia;

    public Usuario() {}

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombreUsu=" + nombreUsu + ", contrasenia=" + contrasenia + ", rol=" + rol + ", token=" + token + '}';
    }

    public Usuario(String nombreUsu, String contrasenia, String rol, String token) {
        this.nombreUsu = nombreUsu;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.token = token;
    }

    public Usuario(int id, String nombreUsu, String contrasenia, String rol, String token) {
        this.id = id;
        this.nombreUsu = nombreUsu;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.token = token;
    }
    private String rol;
    private String token;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nombreUsu
     */
    public String getNombreUsu() {
        return nombreUsu;
    }

    /**
     * @param nombreUsu the nombreUsu to set
     */
    public void setNombreUsu(String nombreUsu) {
        this.nombreUsu = nombreUsu;
    }

    /**
     * @return the contrasenia
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * @param contrasenia the contrasenia to set
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * @return the rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken() {
        String u = this.nombreUsu;
        String k = new Date().toString();
        String t = DigestUtils.sha256Hex(u+";"+k);
        this.token = t;
    }
}
