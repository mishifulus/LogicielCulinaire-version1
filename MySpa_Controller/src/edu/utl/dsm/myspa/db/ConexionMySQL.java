/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utl.dsm.myspa.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Metodo para generar y abrir una coneión con un BD de MySQL
 * Que me devuelve una conexión
 */
/**
 * 
 * @author marti
 */
public class ConexionMySQL {
    Connection conexion;
    
    public Connection open() throws Exception
    {
        //Establecer el friver con el que se va a trabajar
        String driver = "com.mysql.jdbc.Driver"; //Está en la libreria de MySQL Connection
        //Establecer la ruta de conexión
        String url = "jdbc:mysql://127.0.0.1:3306/myspa";
        //Establecer los valores para los permisos de acceso
        String user = "root";
        String password = "CITLALLI123";
        
        //Damos de alta el uso del driver
        Class.forName(driver);
        
        //Abrimos la conexión
        conexion = DriverManager.getConnection(url, user, password);
        
        //Retornamos la conexión que se ha ceado y abierto
        return conexion;
    }
    /**
     * Este método es para cerrar la conexión a bd de MySQL que esté abierta
     */
    
    public void close()
    {
        try {
            if (conexion != null)
                conexion.close();
            } catch (SQLException ex) 
            {
                ex.printStackTrace();
            }
    }
}
