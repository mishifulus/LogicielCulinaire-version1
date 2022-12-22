/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utl.dsm.myspa.controller;

import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Sala;
import edu.utl.dsm.myspa.model.Sucursal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ControllerSala {
    
    public int insert (Sala s) throws Exception{
        String query = "INSERT INTO sala(nombre, descripcion, foto, rutaFoto, estatus, idSucursal)"
                + "VALUES(?,?,?,?,?,?);";
        
        int idGenerado = -1;
        
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        
        ps.setString(1, s.getNombre());
        ps.setString(2, s.getDescripcion());
        ps.setString(3, s.getFoto());
        ps.setString(4, s.getRutaFoto());
        ps.setInt(5, 1);
        ps.setInt(6,s.getSucursal().getId());
        
        ResultSet rs = null;
        
        ps.executeUpdate();
        
        rs = ps.getGeneratedKeys();
        
        if(rs.next()){
            idGenerado = rs.getInt(1);
            s.setId(idGenerado);
        }
        
        rs.close();
        ps.close();
        connMySQL.close();
        
        return idGenerado;
    }
    
    public List<Sala> getAll(int estatus) throws Exception{
        String query = "SELECT * FROM v_sucursales_salas WHERE estatusSala="+estatus+";";
        
        List<Sala> salas = new ArrayList<Sala>();
        
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        PreparedStatement ps = conn.prepareStatement(query);
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            Sala s = new Sala();
            Sucursal su = new Sucursal();
            
            su.setId(rs.getInt("idSucursal"));
            su.setNombre(rs.getString("nombre"));
            su.setDomicilio(rs.getString("domicilio"));
            su.setLatitud(rs.getDouble("latitud"));
            su.setLongitud(rs.getDouble("longitud"));
            su.setEstatus(rs.getInt("estatus"));
            
            s.setId(rs.getInt("idSala"));
            s.setNombre(rs.getString("nombreSala"));
            s.setDescripcion(rs.getString("descripcion"));
            s.setFoto(rs.getString("foto"));
            s.setRutaFoto(rs.getString("rutaFoto"));
            s.setEstatus(rs.getInt("estatusSala"));
            s.setSucursal(su);
            
            salas.add(s);
        }
        rs.close();
        ps.close();
        connMySQL.close();
        
        return salas;
    }
    
    public void delete(int id) throws Exception{
        String query = "UPDATE sala SET estatus=0 WHERE idSala="+id+";";
        
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        PreparedStatement ps = conn.prepareStatement(query);
        
        ps.executeUpdate();
        
        ps.close();
        connMySQL.close();
    }
    
    public void update(Sala s) throws Exception{
        String query = "UPDATE sala SET nombre=?, descripcion=?, foto=?, rutaFoto=?, estatus=?, idSucursal=? WHERE idSala="+s.getId()+";";
        
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        PreparedStatement ps = conn.prepareStatement(query);
        
        ps.setString(1, s.getNombre());
        ps.setString(2, s.getDescripcion());
        ps.setString(3, s.getFoto());
        ps.setString(4, s.getRutaFoto());
        ps.setInt(5, s.getEstatus());
        ps.setInt(6,s.getSucursal().getId());
        
        ps.executeUpdate();
        
        ps.close();
        connMySQL.close();
    }
    
    public List<Sala> search(String filter) throws Exception{
        String query = "SELECT * FROM v_sucursales_salas WHERE (idSucursal LIKE '%"+filter+"%' OR nombre LIKE '%"+filter+"%' OR domicilio LIKE '%"+filter+"%' OR latitud LIKE '%"+filter+"%' OR longitud LIKE '%"+filter+"%' OR estatus LIKE '%"+filter+"%' OR idSala LIKE '%"+filter+"%' OR nombreSala LIKE '%"+filter+"%' OR descripcion LIKE '%"+filter+"%' OR foto LIKE '%"+filter+"%' OR rutaFoto LIKE '%"+filter+"%') AND estatusSala = 1;";
        
        List<Sala> salas = new ArrayList<Sala>();
        
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        PreparedStatement ps = conn.prepareStatement(query);
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            Sala s = new Sala();
            Sucursal su = new Sucursal();
            
            su.setId(rs.getInt("idSucursal"));
            su.setNombre(rs.getString("nombre"));
            su.setDomicilio(rs.getString("domicilio"));
            su.setLatitud(rs.getDouble("latitud"));
            su.setLongitud(rs.getDouble("longitud"));
            su.setEstatus(rs.getInt("estatus"));
            
            s.setId(rs.getInt("idSala"));
            s.setNombre(rs.getString("nombreSala"));
            s.setDescripcion(rs.getString("descripcion"));
            s.setFoto(rs.getString("foto"));
            s.setRutaFoto(rs.getString("rutaFoto"));
            s.setEstatus(rs.getInt("estatusSala"));
            s.setSucursal(su);
            
            salas.add(s);
        }
        rs.close();
        ps.close();
        connMySQL.close();
        
        return salas;
    }
    
    public List<Sala> getAllBySucursal(int idSuc) throws Exception{
        String query = "SELECT * FROM v_sucursales_salas WHERE estatusSala=1 AND idSucursal = "+idSuc+";";
        
        List<Sala> salas = new ArrayList<Sala>();
        
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        PreparedStatement ps = conn.prepareStatement(query);
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            Sala s = new Sala();
            Sucursal su = new Sucursal();
            
            su.setId(rs.getInt("idSucursal"));
            su.setNombre(rs.getString("nombre"));
            su.setDomicilio(rs.getString("domicilio"));
            su.setLatitud(rs.getDouble("latitud"));
            su.setLongitud(rs.getDouble("longitud"));
            su.setEstatus(rs.getInt("estatus"));
            
            s.setId(rs.getInt("idSala"));
            s.setNombre(rs.getString("nombreSala"));
            s.setDescripcion(rs.getString("descripcion"));
            s.setFoto(rs.getString("foto"));
            s.setRutaFoto(rs.getString("rutaFoto"));
            s.setEstatus(rs.getInt("estatusSala"));
            s.setSucursal(su);
            
            salas.add(s);
        }
        rs.close();
        ps.close();
        connMySQL.close();
        
        return salas;
    }
}