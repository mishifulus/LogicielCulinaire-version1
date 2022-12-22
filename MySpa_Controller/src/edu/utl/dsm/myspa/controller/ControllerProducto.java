
package edu.utl.dsm.myspa.controller;

import com.mysql.jdbc.Statement;
import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ControllerProducto
{
    
    /**
     * @param p Se recibe el parametro de tipo producto que contenga los datos a almacenar
     * @return Devuelve el id generado
     * @throws Exception 
     * */
    public int insert(Producto p) throws Exception
    {
        //Definir la sentencia SQL para realizar la inserción de un producto en la BD
        String query = "INSERT INTO producto (nombre, marca, precioUso, estatus)"
                + "VALUES (?, ?, ?, ?)";
        
        //Se declara la variable sobre la que almacena el ID generado
        int idGenerado = -1;
        
        //Se genera un objeto de la conexión y la abrimos
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        //Generar un objeto que lleve la consulta a la BD
        //Que me permita recibir el id generado
        PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        
        //Terminar la sentencia /cargar el objeto pstmt
        pstmt.setString(1, p.getNombre());
        pstmt.setString(2, p.getMarca());
        pstmt.setFloat(3, p.getPrecioUso());
        pstmt.setInt(4, p.getEstatus());
        
        //Generar un objeto que va a guardar el resultado devuelto de la consulta
        ResultSet  rs = null;
        
        //Ejecutamos la consulta
        pstmt.executeUpdate();
        
        //Solicitar al PreparedStatement el valor que se generó
        rs = pstmt.getGeneratedKeys();
        
        //Tomar el valor generado
        if(rs.next())
        {
            idGenerado = rs.getInt(1);
            p.setId(idGenerado);
        }
        
        //Cerrar los objetos de conexión
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        //Devolvemos el id
        return idGenerado;
    }
    
    /**
     * 
     * @param estatus Se recibe en para distinguir activos de inactivos (1,0)
     * @return Se devuelve una lista con los productos encontrados en la bd
     * la lista dinamica devuelve los nodos
     * @throws Exception 
     */
    public List<Producto> getAll(int estatus) throws Exception
    {
        //Definir la consulta SQL
        String query = "SELECT * FROM producto WHERE estatus="+estatus+";";
        
        //Generar la lista de Sucursales que se va a obtener con la consulta
        List<Producto> productos = new ArrayList<Producto>();
        
        //Crear un objeto de conexión a la BD y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        //Generamos el objeto que nos permite enviar y ejecutar la consulta a la BD
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        //Ejecutar la sentencia y recibir el resultado
        ResultSet rs = pstmt.executeQuery();
        
        //Recorremos el RS para sacar los datos de las sucursales
        while(rs.next()) //si está lleno el registro next, retorna true
        {
            //Generar una variable temporal para crear nuevas instancias de Sucursal
            Producto p = new Producto();
            
            //Llenamos los atributos del objeto Sucursal con los datos del RS
            p.setId(rs.getInt("idProducto"));
            p.setNombre(rs.getString("nombre"));
            p.setMarca(rs.getString("marca"));
            p.setPrecioUso(rs.getFloat("precioUso"));
            p.setEstatus(rs.getInt("estatus"));
            
            // Se agrega ese objeto de sucursal a la lista de sucursales
            productos.add(p);
        }
        
        // Cerrar los objetos de conexión 
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        //Se devueve la lista de sucursales
        return productos;
    }
    
    /**
     * Metodo elimina de forma lógica un registr de sucursal
     * @param id Se recibe el id del producto a eliminar
     * @throws Exception 
     */
    public void delete (int id) throws Exception
    {
        //Generar la consulta SQL
        String query = "UPDATE producto SET estatus = 0 WHERE idProducto="+id+";";
        
        //Generar el objeto de conexión y abrirla
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        //Se genera el objeto que lleva la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        //Ejecutamos la consulta
        pstmt.executeUpdate();
        
        //Cerrar los objetos de conexión
        pstmt.close();
        connMySQL.close();
    }
    
    /**
     * @param p Se recibe el parametro de tipo producto que contenga los datos a actualizar
     * @throws Exception 
     * */
    public void update(Producto p) throws Exception
    {
        //Definir la consulta SQL
        String query = "UPDATE producto SET nombre = ?, marca = ?, precioUso = ?, estatus = ? WHERE idProducto = ?";
        
        //Se genera un objeto de la conexión y la abrimos
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        //Generamos el objeto que nos permite enviar y ejecutar la consulta a la BD
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        //Terminar la sentencia /cargar el objeto pstmt
        pstmt.setString(1, p.getNombre());
        pstmt.setString(2, p.getMarca());
        pstmt.setFloat(3, p.getPrecioUso());
        pstmt.setInt(4, p.getEstatus());
        pstmt.setInt(5, p.getId());
        
        //Ejecutamos la consulta
        pstmt.executeUpdate();
        
        //Cerrar los objetos de conexión;
        pstmt.close();
        connMySQL.close();
    }
    
    /**
     * 
     * @param filter Se recibe en para usarlo como comparación entre los registros
     * @return Se devuelve una lista con los productos encontrados en la bd
     * la lista dinamica devuelve los nodos
     * @throws Exception 
     */
    public List<Producto> search (String filter) throws Exception
    {
        //Definir la consulta SQL
        String query = "SELECT * FROM producto WHERE estatus = 1 and "
                + "(idProducto LIKE '%"+filter+"%'"
                + " or marca LIKE '%"+filter+"%' or precioUso LIKE '%"+filter+"%'"
                + " or nombre LIKE '%"+filter+"%');";
        
        //Generar la lista de Sucursales que se va a obtener con la consulta
        List<Producto> productos = new ArrayList<Producto>();
        
        //Se genera un objeto de la conexión y la abrimos
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        //Generamos el objeto que nos permite enviar y ejecutar la consulta a la BD
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        //Ejecutar la sentencia y recibir el resultado
        ResultSet rs = pstmt.executeQuery();
        
        //Recorremos el RS para sacar los datos de los productos
        while(rs.next()) //si está lleno el registro next, retorna true
        {
            //Generar una variable temporal para crear nuevas instancias de Producto
            Producto p = new Producto();
            
            //Llenamos los atributos del objeto Producto con los datos del RS
            p.setId(rs.getInt("idProducto"));
            p.setNombre(rs.getString("nombre"));
            p.setMarca(rs.getString("marca"));
            p.setPrecioUso(rs.getFloat("precioUso"));
            p.setEstatus(rs.getInt("estatus"));
            
            // Se agrega ese objeto de producto a la lista de productos
            productos.add(p);
        }
        
        // Cerrar los objetos de conexión 
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        //Se devueve la lista de productos
        return productos;
    }
}
