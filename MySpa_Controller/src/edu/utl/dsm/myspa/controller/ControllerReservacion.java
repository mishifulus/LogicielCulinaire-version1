/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utl.dsm.myspa.controller;

import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Cliente;
import edu.utl.dsm.myspa.model.Horario;
import edu.utl.dsm.myspa.model.Persona;
import edu.utl.dsm.myspa.model.Reservacion;
import edu.utl.dsm.myspa.model.Sala;
import edu.utl.dsm.myspa.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marti
 */
public class ControllerReservacion {
    
    public List<Horario> getHourAv(String f, int idSala) throws Exception
    {
        String query = "SELECT H1.* FROM horario H1 "
            + " LEFT JOIN "
            + "(SELECT H.* "
            + " FROM horario H "
            + " INNER JOIN reservacion R "
            + " ON H.idHorario = R.idHorario "
            + " WHERE R.idSala="+idSala+" AND R.fecha=STR_TO_DATE('"+f+"','%Y-%m-%d') ) AS SQ2 "
            + "ON H1.idHorario=SQ2.idHorario "
            + "WHERE SQ2.idHorario IS NULL;";
        
        //Se genera un objeto de la conexión y la abrimos
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        List<Horario> horarios = new ArrayList<>();
        
        while (rs.next())
        {
            Horario h = new Horario();
            h.setId(rs.getInt("idHorario"));
            h.setHoraI(rs.getString("horaInicio"));
            h.setHoraF(rs.getString("horaFin"));
            
            horarios.add(h);
        }
        
        rs.close();
        stmt.close();
        conn.close();
        connMySQL.close();
        
        return horarios;
    }
    
    public void insert(Reservacion r) throws Exception
    {
        String query = "INSERT INTO reservacion (estatus, idCliente, idSala, fecha, idHorario) "
                + "VALUES ("+r.getEstatus()+", "+r.getCliente().getId()+", "+r.getSala().getId()
                +", STR_TO_DATE('"+r.getFecha()+"','%Y-%m-%d'), "+r.getHorario().getId()+")";
        
        //Se genera un objeto de la conexión y la abrimos
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        Statement stmt = conn.createStatement();
        stmt.execute(query);
        
        stmt.close();
        conn.close();
        connMySQL.close();
    }
    
    public List<Reservacion> getAll(int estatus) throws Exception
    {
        String query = "SELECT * FROM v_reservacion WHERE estatus="+estatus+";";
        List<Reservacion> reservaciones  = new ArrayList<Reservacion>();
        
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next())
        {
            Reservacion r = new Reservacion();
            Sala s = new Sala();
            Persona p = new Persona();
            Cliente c = new Cliente();
            Horario h = new Horario();
            
            h.setId(rs.getInt("idHorario"));
            h.setHoraI(rs.getString("horaInicio"));
            h.setHoraF(rs.getString("horaFin"));
            
            s.setId(rs.getInt("idSala"));
            s.setNombre(rs.getString("nombreSala"));
            s.setDescripcion(rs.getString("descripcion"));
            s.setFoto(rs.getString("fotoSala"));
            s.setRutaFoto(rs.getString("rutaFotoSala"));
            s.setEstatus(rs.getInt("estatusSala"));
            
            p.setId(rs.getInt("idPersona"));
            p.setNombre(rs.getString("nombre"));
            p.setApellidoP(rs.getString("apellidoPaterno"));
            p.setApellidoM(rs.getString("apellidoMaterno"));
            p.setGenero(rs.getString("genero"));
            p.setDomicilio(rs.getString("domicilio"));
            p.setRfc(rs.getString("rfc"));
            p.setTelefono(rs.getString("telefono"));
            
            c.setId(rs.getInt("idCliente"));
            c.setCorreo(rs.getString("correo"));
            c.setNumeroUni(rs.getString("numeroUnico"));
            c.setPersona(p);
            
            r.setId(rs.getInt("idReservacion"));
            r.setFecha(rs.getString("fecha"));
            r.setEstatus(rs.getInt("estatus"));
            r.setHorario(h);
            r.setSala(s);
            r.setCliente(c);
            
            reservaciones.add(r);
        }
        rs.close();
        ps.close();
        connMySQL.close();
        
        return reservaciones;
    }
    
    public void cancel(int id) throws Exception
    {
        String query = "UPDATE reservacion SET estatus = 0 WHERE idReservacion ="+id+";";
        
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        PreparedStatement ps = conn.prepareStatement(query);
        ps.executeUpdate();
        
        ps.close();
        connMySQL.close();
    }
    
    public List<Reservacion> search(String filter) throws Exception
    {
        String query = "SELECT * FROM v_reservacion WHERE (nombre LIKE '%"+filter+"%' OR apellidoPaterno LIKE '%"+filter+"%' OR apellidoMaterno LIKE '%"+filter+"%' OR genero LIKE '%"+filter+"%' OR domicilio LIKE '%"+filter+"%' OR telefono LIKE '%"+filter+"%' OR rfc LIKE '%"+filter+"%' OR numeroUnico LIKE '%"+filter+"%' OR correo LIKE '%"+filter+"%' OR fecha LIKE '%"+filter+"%' OR horaInicio LIKE '%"+filter+"%' OR horaFin LIKE '%"+filter+"%' OR nombreSala LIKE '%"+filter+"%' OR descripcion LIKE '%"+filter+"%') AND estatus = 1;";
        List<Reservacion> reservaciones  = new ArrayList<Reservacion>();
        
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next())
        {
            Reservacion r = new Reservacion();
            Sala s = new Sala();
            Persona p = new Persona();
            Cliente c = new Cliente();
            Horario h = new Horario();
            
            h.setId(rs.getInt("idHorario"));
            h.setHoraI(rs.getString("horaInicio"));
            h.setHoraF(rs.getString("horaFin"));
            
            s.setId(rs.getInt("idSala"));
            s.setNombre(rs.getString("nombreSala"));
            s.setDescripcion(rs.getString("descripcion"));
            s.setFoto(rs.getString("fotoSala"));
            s.setRutaFoto(rs.getString("rutaFotoSala"));
            s.setEstatus(rs.getInt("estatusSala"));
            
            p.setId(rs.getInt("idPersona"));
            p.setNombre(rs.getString("nombre"));
            p.setApellidoP(rs.getString("apellidoPaterno"));
            p.setApellidoM(rs.getString("apellidoMaterno"));
            p.setGenero(rs.getString("genero"));
            p.setDomicilio(rs.getString("domicilio"));
            p.setRfc(rs.getString("rfc"));
            p.setTelefono(rs.getString("telefono"));
            
            c.setId(rs.getInt("idCliente"));
            c.setCorreo(rs.getString("correo"));
            c.setNumeroUni(rs.getString("numeroUnico"));
            c.setPersona(p);
            
            r.setId(rs.getInt("idReservacion"));
            r.setFecha(rs.getString("fecha"));
            r.setEstatus(rs.getInt("estatus"));
            r.setHorario(h);
            r.setSala(s);
            r.setCliente(c);
            
            reservaciones.add(r);
        }
        rs.close();
        ps.close();
        connMySQL.close();
        
        return reservaciones;
    }
}
