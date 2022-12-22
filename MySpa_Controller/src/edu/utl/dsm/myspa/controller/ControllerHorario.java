/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utl.dsm.myspa.controller;

import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Horario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ControllerHorario {
    
    public int insert(Horario h) throws Exception {
        String query = "INSERT INTO horario (idHorario, horaInicio, horaFin)" + "VALUES(?,?,?)";
        int idGenerado = -1;
        ConexionMySQL connMYSQL = new ConexionMySQL();
        Connection conn = connMYSQL.open();
        PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, String.valueOf(h.getId()));
        pstmt.setString(2, h.getHoraI());
        pstmt.setString(3, h.getHoraF());
        ResultSet rs = null;
        pstmt.executeUpdate();
        rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            idGenerado = rs.getInt(1);
            h.setId(idGenerado);
        }
        rs.close();
        pstmt.close();
        connMYSQL.close();
        return idGenerado;
    }
    
    public void delete(int id) throws Exception {
        String query = "DELETE FROM horario WHERE idHorario=" + id + ";";
        ConexionMySQL connMYSQL = new ConexionMySQL();
        Connection conn = connMYSQL.open();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.executeUpdate();
        pstmt.close();
        connMYSQL.close();
    }
    
    public void update(Horario h) throws Exception {
        String query = "UPDATE horario SET horaInicio=?, horaFin=? WHERE idHorario= ?;";
        ConexionMySQL connMYSQL = new ConexionMySQL();
        Connection conn = connMYSQL.open();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, h.getHoraI());
        pstmt.setString(2, h.getHoraF());
        pstmt.setInt(3, h.getId());
        pstmt.executeUpdate();
        pstmt.close();
        connMYSQL.close();
    }
    
    public List<Horario> getAll() throws Exception {
        //Definir la consulta sql
        String query = "SELECT * FROM horario ";
        //Generar lista de Sucursales que se obtendra con la consulta
        List<Horario> horarios = new ArrayList<Horario>();
        //Crear un objeto de la coneccion a la BD
        ConexionMySQL connMYSQL = new ConexionMySQL();
        Connection conn = connMYSQL.open();
        //Generamos el ojeo que nos permite enviar y ejecutar la consulta a la BD
        PreparedStatement pstmt = conn.prepareStatement(query);
        //Ejecutar la sentencia y recibir el resultado
        ResultSet rs = pstmt.executeQuery();
        //Recorremos el rs para sacar los datos de las sucursales
        while (rs.next()) {
            //Creamos una variable temporal para crear nuevas instancias de sucursal
            Horario h = new Horario();
            //Llenamos los atributos del obj Sucursal con los datos del rs
            h.setId(rs.getInt("idHorario"));
            h.setHoraI(rs.getString("horaInicio"));
            h.setHoraF(rs.getString("horaFin"));
            horarios.add(h);
        }
        //cerrar los objetos de Conexion
        rs.close();
        pstmt.close();
        connMYSQL.close();
        //Devolvemos nuestra lista
        return horarios;
    }
    
    public List<Horario> search(String filter) throws Exception {
        String query = "SELECT * FROM horario WHERE idHorario like concat('%','" + filter + "', '%') "
                + "or horaInicio like concat('%','" + filter + "', '%') "
                + "or horaFin like concat('%','" + filter + "', '%');";
        //Generar lista de Sucursales que se obtendra con la consulta
        List<Horario> horarios = new ArrayList<Horario>();
        //Crear un objeto de la coneccion a la BD
        ConexionMySQL connMYSQL = new ConexionMySQL();
        Connection conn = connMYSQL.open();
        //Generamos el ojeo que nos permite enviar y ejecutar la consulta a la BD
        PreparedStatement pstmt = conn.prepareStatement(query);
        //Ejecutar la sentencia y recibir el resultado
        ResultSet rs = pstmt.executeQuery();
        //Recorremos el rs para sacar los datos de las sucursales
        while (rs.next()) {
            //Creamos una variable temporal para crear nuevas instancias de sucursal
            Horario h = new Horario();
            //Llenamos los atributos del obj Sucursal con los datos del rs
            h.setId(rs.getInt("idHorario"));
            h.setHoraI(rs.getString("horaInicio"));
            h.setHoraF(rs.getString("horaFin"));
            horarios.add(h);
        }
        //cerrar los objetos de Conexion
        rs.close();
        pstmt.close();
        connMYSQL.close();
        //Devolvemos nuestra lista ya sea vacia o llenada

        return horarios;

    }
}
