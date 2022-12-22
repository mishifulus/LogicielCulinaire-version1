
package edu.utl.dsm.myspa.controller;

import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Tratamiento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ControllerTratamiento {
   
    public int insert(Tratamiento t) throws Exception{
        /* Definir la sentencia SQL para realizar la inserción de un tratamiento
           en la base de datos */
        String query = "INSERT INTO tratamiento(nombre, descripcion, costo, estatus)"
                     + "VALUES (?, ?, ?, ?)";
        
        /* Se declara la vairable sobre la que almacena el ID generado */
        int idGenerado = -1;
        
        /* Se generaa un objeto de la conexión y la abrimos */
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        /* Generar un objeto que lleve la consulta a la base de datos y que 
           permita recibir el id generado */
        PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        
        /* Terminar la sentencia / cargar el objeto pstms */
        pstmt.setString(1, t.getNombre());
        pstmt.setString(2, t.getDescripcion());
        pstmt.setFloat(3, t.getCosto());
        pstmt.setInt(4, t.getEstatus());
        
        /* Generar un objeto que va a guardar el resultado devuelto de la consulta */
        ResultSet rs = null;
        
        /* Ejecutamos la consulta */
        pstmt.executeUpdate();
        
        /* Solicitar al PreparedStatement el valor que se genero(id) */
        rs = pstmt.getGeneratedKeys();
        
        /* Tomar el valor generado */
        if(rs.next()){
            idGenerado = rs.getInt(1);
            t.setId(idGenerado);
        }
        
        /* Cerrar los objetos de conexión */
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        /* Devolver el ID */
        return idGenerado;
        
    }
    
    public List<Tratamiento> getAll(int estatus) throws Exception{
        //Definir la consulta SQL
        String query = "SELECT * FROM myspa.tratamiento WHERE estatus="+estatus+";";
        
        //Generar la lista de Sucursales que se va a obtener con la consulta
        List<Tratamiento> tratamiento = new ArrayList<Tratamiento>();
        
        //Crear un objeto de la conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        //Generamos el objeto que nos permite enviar y ejecutar la consulta a la BD
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        //Ejecutar la sentencia y recibir el resultado
        ResultSet rs = pstmt.executeQuery();
        
        //Recorremos el RS para sacar los datos de las sucursales
        while(rs.next()){
            
            //Generar una variable temporal para crear nuevas instancias de Sucursal
            Tratamiento t = new Tratamiento();
            
            //LLenamos los atributos del objeto Sucursal con los datos del RS
            t.setId(rs.getInt("idTratamiento"));
            t.setNombre(rs.getString("nombre"));
            t.setDescripcion(rs.getString("descripcion"));
            t.setCosto(rs.getFloat("costo"));
            t.setEstatus(rs.getInt("estatus"));
            
            //Se agrega ese objeto de Sucursal a la lista de sucursales
            tratamiento.add(t);
        }
        
        //Cerrar los objetos de conexion a la BD
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        //Se devuelve la lista de sucursales
        return tratamiento;
    }
    
    public void delete(int id) throws Exception{
        //Generar la consulta SQL
        String query = "UPDATE tratamiento SET estatus=0 WHERE idTratamiento="+id+";";
        
        //Generar el objeto de conexción y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        //Se genera el objeto que lleva la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        //Ejecutamos la consulta
        pstmt.executeUpdate();
        
        //Cerrar los objetos de conexión a la BD
        pstmt.close();
        connMySQL.close();
    }
    
    public void update(Tratamiento t) throws Exception
    {
        //Definir la consulta SQL 
        String query = "UPDATE tratamiento SET nombre = '" + t.getNombre() + 
                                        "', descripcion = '" + t.getDescripcion() + 
                                        "', costo=" + t.getCosto() +  
                                        " WHERE idTratamiento = " + t.getId() + ";";

        //Se genera un objeto de la BD y la abrimos
        ConexionMySQL conMySQL = new ConexionMySQL();
        Connection conn = conMySQL.open();

        //Generamos el objeto que nos permite enviar y ejecutar la consulta a la base de datos
        PreparedStatement pstmt = conn.prepareStatement(query);

        //Ejecutar la sentencia
        pstmt.executeUpdate();

        //Cerrar los objetos de conexión
        pstmt.close();
        conMySQL.close();
    }
    
    public List<Tratamiento> search(String filter) throws Exception
    {
        //Definir la consulta SQL 
        String query = "SELECT * FROM tratamiento WHERE idTratamiento LIKE '%" + filter + 
                                                                     "%' OR nombre LIKE '%" + filter + 
                                                                     "%' OR descripcion LIKE '%" + filter +
                                                                     "%' OR costo LIKE '%" + filter +
                                                                     "%' OR estatus LIKE '%" + filter + "%';";

        //Generar la lista de sucursales que se va a obtener la consulta
        List<Tratamiento> stratamieno = new ArrayList<Tratamiento>();

        //Se genera un objeto de la BD y la abrimos
        ConexionMySQL conMySQL = new ConexionMySQL();
        Connection conn = conMySQL.open();

        //Generamos el objeto que nos permite enviar y ejecutar la consulta a la base de datos
        PreparedStatement pstmt = conn.prepareStatement(query);

        //Ejecutar la sentencia y recibir el resultado
        ResultSet rs = pstmt.executeQuery();

        // Recorremos el ResultSet para sacar los datos de las sucursales
        while(rs.next())
        {
            //Generar una variable temporal para crear nuevas instancias de Sucursal
            Tratamiento t = new Tratamiento();

            //Llenamos los atributos del objeto Sucursal con los datos del ResultSet
            t.setId(rs.getInt("idTratamiento"));
            t.setNombre(rs.getString("nombre"));
            t.setDescripcion(rs.getString("descripcion"));
            t.setCosto(rs.getFloat("costo"));
            t.setEstatus(rs.getInt("estatus"));

            //Se agrega ese objeto de sucursal a la lista de Sucursales
            stratamieno.add(t);
        }

        //Cerrar los objetos de conexión
        rs.close();
        pstmt.close();
        conMySQL.close();

        //Se devuelve la lista de las sucursales
        return stratamieno;
    }
}
