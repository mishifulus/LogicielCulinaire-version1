

package edu.utl.dsm.myspa.controller;

import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Cliente;
import edu.utl.dsm.myspa.model.Persona;
import edu.utl.dsm.myspa.model.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *  Esta clase contiene los métodos necesarios para mantener la
 *  persistencia y consulta de informacion de los clientes
 *  en la base de datos.
 * @author Estudiabaantes
 */
public class ControllerCliente {
    
    /**
     * Inserta un registro de {@link Cliente} en la base de datos.
     * 
     * @param c Es el objeto de tipo {@link Cliente}, el cual
     *          contiene los datos que seran insertados dentro del nuevo
     *          registro.
     * @return  Devuelve el ID que se genera para el Cliente, después de su
     *          insercion.
     * @throws Exception 
     */
    public int insert (Cliente c) throws Exception
    {
        //Definimos la consulta SQL que invoca al Stored Procedure:
        String sql = "{call insertarCliente(?, ?, ?, ?, ?, ?, ?," + //Datos de persona
                                            "?, ?, ?," + //Datos de usuario
                                            "?," + //Datos de cliente
                                            "?, ?, ?, ?)}"; //Valores de retorno
        
        //Aquí guardaremoslos ID's que se generarán:
        int idPersonaGenerado = -1;
        int idUsuarioGenerado = -1;
        int idClienteGenerado = -1;
        String NumeroUni = "";
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);
        
        //Establecemos los parámetros de los datos personales en el orden
        //en que los pide el procedimiento almacenado, comenzando en 1:
        cstmt.setString(1, c.getPersona().getNombre());
        cstmt.setString(2, c.getPersona().getApellidoP());
        cstmt.setString(3, c.getPersona().getApellidoM());
        cstmt.setString(4, c.getPersona().getGenero());
        cstmt.setString(5, c.getPersona().getDomicilio());
        cstmt.setString(6, c.getPersona().getTelefono());
        cstmt.setString(7, c.getPersona().getRfc());
        
        //Establecemos los parámetros de los datos de Usuario:
        cstmt.setString(8, c.getUsuario().getNombreUsu());
        cstmt.setString(9, c.getUsuario().getContrasenia());
        cstmt.setString(10, c.getUsuario().getRol());
        
        //Establecemos los parámetros de los datos de Cliente:        
        cstmt.setString(11, c.getCorreo());
        
        //Registramos los parámetros de salida:
        cstmt.registerOutParameter(13, java.sql.Types.INTEGER);
        cstmt.registerOutParameter(12, Types.VARCHAR); //NumUni
        cstmt.registerOutParameter(14, Types.INTEGER);
        cstmt.registerOutParameter(15, Types.INTEGER);
        
        //Ejecutamos el Stored Procedure:
        cstmt.executeUpdate();
        
        //Recuperamos los ID's generados:
        NumeroUni = cstmt.getString(12);
        idPersonaGenerado = cstmt.getInt(13);
        idUsuarioGenerado = cstmt.getInt(14);
        idClienteGenerado = cstmt.getInt(15);
        
        //Los guardamos en el objeto Cliente que nos pasaron como parámetro:
        c.setNumeroUni(NumeroUni);
        c.getPersona().setId(idPersonaGenerado);
        c.getUsuario().setId(idUsuarioGenerado);
        c.setId(idClienteGenerado);
        
        //Cerramos los objetos de Base de Datos:
        cstmt.close();
        connMySQL.close();
        
        //Devolvemos el ID de Empleado generado:
        return idClienteGenerado;
    }
    
    
    /**
     * Actualiza un registro de {@link Cliente}, previamente existente, 
     * en la base de datos.
     * 
     * @param c Es el objeto de tipo {@link Cliente}, el cual
     *          contiene los datos que seran insertados dentro del nuevo
     *          registro.
     * @throws Exception 
     */
    public void update(Cliente c) throws Exception
    {
        //Definimos la consulta SQL que invoca al Stored Procedure:
        String sql =    "{call actualizarCliente(?, ?, ?, ?, ?, ?, ?," + //Datos de persona
                                            "?, ?, ?," + //Datos de usuario
                                            "?, ?," + //Datos de cliente
                                            "?, ?, ?)}"; //IDs de tablas relacionadas
                
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);
        
        //Establecemos los parámetros de los datos personales en el orden
        //en que los pide el procedimiento almacenado, comenzando en 1:
        cstmt.setString(1, c.getPersona().getNombre());
        cstmt.setString(2, c.getPersona().getApellidoP());
        cstmt.setString(3, c.getPersona().getApellidoM());
        cstmt.setString(4, c.getPersona().getGenero());
        cstmt.setString(5, c.getPersona().getDomicilio());
        cstmt.setString(6, c.getPersona().getTelefono());
        cstmt.setString(7, c.getPersona().getRfc());
        
        //Establecemos los parámetros de los datos de Usuario:
        cstmt.setString(8, c.getUsuario().getNombreUsu());
        cstmt.setString(9, c.getUsuario().getContrasenia());
        cstmt.setString(10, c.getUsuario().getRol());
        
        //Establecemos los parámetros de los datos de Cliente:        
        cstmt.setString(11, c.getCorreo());
        cstmt.setString(12, c.getNumeroUni());
        
        //Establecemos los ID's de las tablas relacionadas:
        cstmt.setInt(13, c.getPersona().getId());
        cstmt.setInt(14, c.getUsuario().getId());
        cstmt.setInt(15, c.getId());
        
        //Ejecutamos el Stored Procedure:
        cstmt.executeUpdate();
        
        //Cerramos los objetos de Base de Datos:
        cstmt.close();
        connMySQL.close();
    }
    
    
    /**
     * Elimina un registro de {@link Cliente} en la base de datos.
     * 
     * @param id Es el ID del {@link Cliente} que se desea eliminar.
     * @throws Exception 
     */
    public void delete(int id) throws Exception
    {
        //Generar la consulta SQL
        String query = "UPDATE cliente SET estatus = 0 WHERE idCliente="+id+";";
        
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
     * Busca un registro de {@link Cliente} en la base de datos.
     * 
     * @param filter Es el la palabra del {@link Cliente} que se desea buscar.
     * @return  Devuelve el {@link Cliente} que se encuentra en la base de datos,
     *          basado en la coincidencia filter pasado como parámetro.
     *          Si no es encontrado un {@link Cliente} ,
     *          el método devolverá <code>null</code>.
     * @throws Exception 
     */
    public List<Cliente> search(String filter) throws Exception
    {
        //Definir la consulta SQL
        String query = "SELECT * FROM v_clientes WHERE estatus = 1 and (idPersona LIKE '%"+filter+"%' or "
                + "nombre LIKE '%"+filter+"%' or apellidoPaterno LIKE '%"+filter+"%' or apellidoMaterno LIKE '%"+filter+"%' or "
                + "genero LIKE '%"+filter+"%' or domicilio LIKE '%"+filter+"%' or telefono LIKE '%"+filter+"%' or rfc LIKE '%"+filter+"%' or "
                + "idCliente LIKE '%"+filter+"%' or numeroUnico LIKE '%"+filter+"%' or correo LIKE '%"+filter+"%' or "
                + "idUsuario LIKE '%"+filter+"%' or nombreUsuario LIKE '%"+filter+"%' or rol LIKE '%"+filter+"%');";
        
        //Generar la lista de Sucursales que se va a obtener con la consulta
        List<Cliente> clientes = new ArrayList<Cliente>();
        
        //Una variable temporal para crear nuevos objetos de tipo Empleado:
        Cliente c = null;
        
        //Se genera un objeto de la conexión y la abrimos
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        //Generamos el objeto que nos permite enviar y ejecutar la consulta a la BD
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        //Aquí guardaremos los resultados de la consulta:
        ResultSet rs = pstmt.executeQuery();
        
        //Iteramos el conjunto de registros devuelto por la BD.
        //"Si hay un siguiente registro, nos movemos":
        while (rs.next())
        {
            c = fill(rs);
            //Agregamos el objeto de tipo Empleado a la lista dinámica:
            clientes.add(c);
        }
        
        //Cerramos los objetos de BD:
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        //Devolvemos la lista dinámica con objetos de tipo Empleado dentro:
        return clientes;
    }
    
    /**
     * Consulta y devuelve los registros de clientes encontrados.
     * 
     * Los registros encontrados se devuelven en forma de una lista dinamica
     * (List&lt;{@link Cliente}&rt;) que contiene dentro los objetos de tipo 
     * {@link Cliente}.
     * 
     * @param estatus  Es el termino de coincidencia parcial que condicionara
     * @return  Devuelve el listado de clientes encontrados 
     *          en la base de datos, en forma de una lista dinamica
     *          <code>List&lt;{@link Cliente}&rt;</code>.
     *          Si la base de datos no tiene algun registro de cliente, se 
     *          devuelve una lista vacia (NO SE DEVUELVE <code>null</code>!).
     * @throws Exception 
     */
    public List<Cliente> getAll(int estatus) throws Exception
    {
        //La consulta SQL a ejecutar:
        String sql = "SELECT * FROM v_clientes WHERE estatus="+estatus+";";
        
        //La lista dinámica donde guardaremos objetos de tipo Cliente
        //por cada registro que devuelva la BD:
        List<Cliente> clientes = new ArrayList<Cliente>();
        
        //Una variable temporal para crear nuevos objetos de tipo Cliente:
        Cliente c = null;
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto ejecutaremos la consulta:
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        //Aquí guardaremos los resultados de la consulta:
        ResultSet rs = pstmt.executeQuery();
        
        //Iteramos el conjunto de registros devuelto por la BD.
        //"Si hay un siguiente registro, nos movemos":
        while (rs.next())
        {
            c = fill(rs);
            //Agregamos el objeto de tipo Cliente a la lista dinámica:
            clientes.add(c);
        }
        
        //Cerramos los objetos de BD:
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        //Devolvemos la lista dinámica con objetos de tipo Cliente dentro:
        return clientes;
    }
    
    
    //Hace el llenado de las variables de los métodos buscar y getAll
    private Cliente fill(ResultSet rs) throws Exception
    {
        //Una variable temporal para crear nuevos objetos de tipo Cliente:
        Cliente c = new Cliente();
        
        //Una variable temporal para crear nuevos objetos de tipo Persona:
        Persona p = new Persona();
        
        //Una variable temporal para crear nuevos objetos de tipo Usuario:
        Usuario u = new Usuario();
        

        //Llenamos sus datos:
        p.setApellidoM(rs.getString("apellidoMaterno"));
        p.setApellidoP(rs.getString("apellidoPaterno"));
        p.setDomicilio(rs.getString("domicilio"));
        p.setGenero(rs.getString("genero"));
        p.setId(rs.getInt("idPersona"));
        p.setNombre(rs.getString("nombre"));
        p.setRfc(rs.getString("rfc"));
        p.setTelefono(rs.getString("telefono"));

        //Creamos un nuevo objeto de tipo Usuario:
        u.setContrasenia(rs.getString("contrasenia"));
        u.setId(rs.getInt("idUsuario"));
        u.setNombreUsu(rs.getString("nombreUsuario"));
        u.setRol(rs.getString("rol"));

        //Establecemos sus datos personales:
        c.setId(rs.getInt("idCliente"));
        c.setNumeroUni(rs.getString("numeroUnico"));           
        c.setCorreo(rs.getString("correo"));
        c.setEstatus(rs.getInt("estatus"));

        //Establecemos su persona:
        c.setPersona(p);

        //Establecemos su Usuario:
        c.setUsuario(u);
        
        return c;
    }
}
