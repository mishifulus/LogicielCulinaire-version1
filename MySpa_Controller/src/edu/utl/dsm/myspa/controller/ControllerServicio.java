/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utl.dsm.myspa.controller;

import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Producto;
import edu.utl.dsm.myspa.model.Servicio;
import edu.utl.dsm.myspa.model.ServicioT;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author marti
 */
public class ControllerServicio {
       
    public int insert(Servicio s) throws Exception
    {
        //Preparamos las consultas
        String sqlServicio = "INSERT INTO servicio (fecha, idReservacion, idEmpleado) " +
                             "VALUES(?, ?, ?)";
        String sqlServicioTratamiento = "INSERT INTO servicio_tratamiento(idTratamiento, idServicio) " +
                                        "VALUES(?, ?)";
        String sqlServicioTratamientoProducto = "INSERT INTO servicio_tratamiento_producto " + 
                                                "(idServicioTratamiento, idProducto, precioUso) " + 
                                                "VALUES(?, ?, ?)";
        String sqlReservacion = "UPDATE reservacion SET estatus = 2 WHERE idReservacion = ?";
        
        //Nos conectamos a la BD
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        //Preparamos los statements que vamos a ocupar
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        PreparedStatement pstmt4 = null;
        //Preparamos el rs
        ResultSet rs = null;
        
        //Preparamos objetos para colocar las listas que se incluyen en el servicio y un contador
        int cont = 0;
        List<ServicioT> serviciosTratamientos = null;
        List<Producto> productos = null;
        
        //Deshabilitamos la persistencia automatica de los datos:
        //para comenzar la transaccion
        conn.setAutoCommit(false);
        try
        {
            //Generamos un PreparedStatement que ejecutara la consulta************************************
            //y le indicamos que nos devuelva los ID's que se generen (1).
            pstmt1 = conn.prepareStatement(sqlServicio, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt1.setString(1, s.getFecha());
            pstmt1.setInt(2, s.getReservacion().getId());
            pstmt1.setInt(3, s.getEmpleado().getId());
                                    
            //Ejecutamos la consulta:
            pstmt1.executeUpdate();
            //Recuperamos el ID que se genero del servicio:
            rs = pstmt1.getGeneratedKeys();
            rs.next();
            s.setId(rs.getInt(1));
            //Cerramos el ResultSet:
            rs.close();
            
            //Preparamos la segunda consulta**************************************************************
            pstmt2 = conn.prepareStatement(sqlServicioTratamiento, PreparedStatement.RETURN_GENERATED_KEYS);
            //Preparamos un lote de ejecuciones SQL:
            for (int i = 0; i < s.getServiciosT().size(); i++)
            {
                //Llenamos los datos del PreparedStatement:
                pstmt2.setInt(1, s.getServiciosT().get(i).getTratamiento().getId());
                pstmt2.setInt(2, s.getId());
                //Agregamos la consulta al lote:
                pstmt2.addBatch();
            }
            //Ejecutamos todo el lote de instrucciones:
            pstmt2.executeBatch();
            //Recuperamos los ID's generados:
            rs = pstmt2.getGeneratedKeys();
            
            //Vamos asignando cada uno de los ID's de servicioTratamiento generados
            while (rs.next())
                s.getServiciosT().get(cont++).setId(rs.getInt(1));
            
            //Cerramos el ResultSet:
            rs.close();
            
            //Preparamos la tercer consulta**************************************************************
            pstmt3 = conn.prepareStatement(sqlServicioTratamientoProducto);
            serviciosTratamientos = s.getServiciosT();
            
            //Recorremos la lista que contiene los ServicioTratamiento:
            for (int i = 0; i < s.getServiciosT().size(); i++)
            {
                //Recuperamos el ServicioTratamiento de la posicion i
                //y le pedimos sus productos:
                productos = serviciosTratamientos.get(i).getProductos();
                
                //Recorremos la lista de productos:
                for (int j = 0; j < productos.size(); j++)
                {
                    //Llenamos los parametros del PreparedStatement:
                    pstmt3.setInt(1, serviciosTratamientos.get(i).getId());
                    pstmt3.setInt(2, productos.get(j).getId());
                    pstmt3.setFloat(3, productos.get(j).getPrecioUso());
                    
                    //Agregamos la consulta al lote:
                    pstmt3.addBatch();
                }
            }
            //Ejecutamos el lote de instrucciones:
            pstmt3.executeBatch();
            
            //Preparamos la cuarta consulta**************************************************************
            pstmt4 = conn.prepareStatement(sqlReservacion);
            pstmt4.setInt(1, s.getReservacion().getId());
            pstmt4.executeUpdate();
            
            //Persistimos los cambios realizados en la BD:
            conn.commit();
        }
        catch (Exception e)
        {
            //Si algo falla:
            //Imprimimos la excepcion:
            e.printStackTrace();
            
            //Hacemos un RollBack:
            conn.rollback();
            
            //Cerramos la conexion:
            connMySQL.close();
            
            //Lanzamos la excepcion:
            throw e;
        }
        conn.setAutoCommit(true);
        
        pstmt1.close();
        pstmt2.close();
        pstmt3.close();
        pstmt4.close();
        conn.close();
        connMySQL.close();
        
        return s.getId();
    }
}
