package edu.utl.dsm.myspa.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Empleado;
import edu.utl.dsm.myspa.model.Persona;
import edu.utl.dsm.myspa.model.Usuario;

/**
 *  Esta clase contiene los métodos necesarios para mantener la
 *  persistencia y consulta de informacion de los empleados 
 *  en la base de datos.
 * @author LiveGrios
 */
public class ControllerEmpleado
{
    /**
     * Inserta un registro de {@link Empleado} en la base de datos.
     * 
     * @param e Es el objeto de tipo {@link Empleado}, el cual
     *          contiene los datos que seran insertados dentro del nuevo
     *          registro.
     * @return  Devuelve el ID que se genera para el Empleado, después de su
     *          insercion.
     * @throws Exception 
     */
    public int insert(Empleado e) throws Exception
    {
        //Definimos la consulta SQL que invoca al Stored Procedure:
        String sql =    "{call insertarEmpleado(?, ?, ?, ?, ?, ?, ?, " + //Datos Persona
                                               "?, ?, ?, " +   //Datos Usuario
                                               "?, ?, ?, " + //Datos Empleado
                                               "?, ?, ?, ?)}"; //Valores de Retorno
        
        //Aquí guardaremoslos ID's que se generarán:
        int idPersonaGenerado = -1;
        int idUsuarioGenerado = -1;
        int idEmpleadoGenerado = -1;
        String numEmpleadoGenerado = "";
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);
        
        //Establecemos los parámetros de los datos personales en el orden
        //en que los pide el procedimiento almacenado, comenzando en 1:
        cstmt.setString(1, e.getPersona().getNombre());
        cstmt.setString(2, e.getPersona().getApellidoP());
        cstmt.setString(3, e.getPersona().getApellidoM());
        cstmt.setString(4, e.getPersona().getGenero());
        cstmt.setString(5, e.getPersona().getDomicilio());
        cstmt.setString(6, e.getPersona().getTelefono());
        cstmt.setString(7, e.getPersona().getRfc());
        
        //Establecemos los parámetros de los datos de Usuario:
        cstmt.setString(8, e.getUsuario().getNombreUsu());
        cstmt.setString(9, e.getUsuario().getContrasenia());
        cstmt.setString(10, e.getUsuario().getRol());
        
        //Establecemos los parámetros de los datos de Empleado:        
        cstmt.setString(11, e.getPuesto());
        cstmt.setString(12, e.getFoto());
        cstmt.setString(13, e.getRutaFoto());
        
        //Registramos los parámetros de salida:
        cstmt.registerOutParameter(14, java.sql.Types.INTEGER);
        cstmt.registerOutParameter(15, Types.INTEGER);
        cstmt.registerOutParameter(16, Types.INTEGER);
        cstmt.registerOutParameter(17, Types.VARCHAR);
        
        //Ejecutamos el Stored Procedure:
        cstmt.executeUpdate();
        
        //Recuperamos los ID's generados:
        idPersonaGenerado = cstmt.getInt(14);
        idUsuarioGenerado = cstmt.getInt(15);
        idEmpleadoGenerado = cstmt.getInt(16);
        numEmpleadoGenerado = cstmt.getString(17);
        
        //Los guardamos en el objeto Empleado que nos pasaron como parámetro:
        e.getPersona().setId(idPersonaGenerado);
        e.getUsuario().setId(idUsuarioGenerado);
        e.setId(idEmpleadoGenerado);
        e.setNumEmpleado(numEmpleadoGenerado);
        
        //Cerramos los objetos de Base de Datos:
        cstmt.close();
        connMySQL.close();
        
        //Devolvemos el ID de Empleado generado:
        return idEmpleadoGenerado;
    }
    
    /**
     * Actualiza un registro de {@link Empleado}, previamente existente, 
     * en la base de datos.
     * 
     * @param e Es el objeto de tipo {@link Empleado}, el cual
     *          contiene los datos que seran insertados dentro del nuevo
     *          registro.
     * @throws Exception 
     */
    public void update(Empleado e) throws Exception
    {
        //Definimos la consulta SQL que invoca al Stored Procedure:
        String sql =    "{call actualizarEmpleado(?, ?, ?, ?, ?, ?, ?, " + //Datos Persona
                                               "?, ?, ?, " +   //Datos Usuario
                                               "?, ?, ?, " + //Datos Empleado
                                               "?, ?, ?)}"; //IDs de tablas relacionadas
                
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);
        
        //Establecemos los parámetros de los datos personales en el orden
        //en que los pide el procedimiento almacenado, comenzando en 1:
        cstmt.setString(1, e.getPersona().getNombre());
        cstmt.setString(2, e.getPersona().getApellidoP());
        cstmt.setString(3, e.getPersona().getApellidoM());
        cstmt.setString(4, e.getPersona().getGenero());
        cstmt.setString(5, e.getPersona().getDomicilio());
        cstmt.setString(6, e.getPersona().getTelefono());
        cstmt.setString(7, e.getPersona().getRfc());
        
        //Establecemos los parámetros de los datos de Usuario:
        cstmt.setString(8, e.getUsuario().getNombreUsu());
        cstmt.setString(9, e.getUsuario().getContrasenia());
        cstmt.setString(10, e.getUsuario().getRol());
        
        //Establecemos los parámetros de los datos de Empleado:        
        cstmt.setString(11, e.getPuesto());
        cstmt.setString(12, e.getFoto());
        cstmt.setString(13, e.getRutaFoto());
        
        //Establecemos los ID's de las tablas relacionadas:
        cstmt.setInt(14, e.getPersona().getId());
        cstmt.setInt(15, e.getUsuario().getId());
        cstmt.setInt(16, e.getId());
        
        //Ejecutamos el Stored Procedure:
        cstmt.executeUpdate();
        
        //Cerramos los objetos de Base de Datos:
        cstmt.close();
        connMySQL.close();
    }
    
    /**
     * Elimina un registro de {@link Empleado} en la base de datos.
     * 
     * @param id Es el ID del {@link Empleado} que se desea eliminar.
     * @throws Exception 
     */
    public void delete(int id) throws Exception
    {
        //Generar la consulta SQL
        String query = "UPDATE empleado SET estatus = 0 WHERE idEmpleado="+id+";";
        
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
     * Busca un registro de {@link Empleado} en la base de datos.
     * 
     * @param filter Es el la palabra del {@link Empleado} que se desea buscar.
     * @return  Devuelve el {@link Empleado} que se encuentra en la base de datos,
     *          basado en la coincidencia filter pasado como parámetro.
     *          Si no es encontrado un {@link Empleado} ,
     *          el método devolvera <code>null</code>.
     * @throws Exception 
     */
    public List<Empleado> search(String filter) throws Exception
    {
        //Definir la consulta SQL
        String query = "SELECT * FROM v_empleados WHERE estatus = 1 and (idPersona LIKE '%"+filter+"%' or "
                + "nombre LIKE '%"+filter+"%' or apellidoPaterno LIKE '%"+filter+"%' or apellidoMaterno LIKE '%"+filter+"%' or "
                + "genero LIKE '%"+filter+"%' or domicilio LIKE '%"+filter+"%' or telefono LIKE '%"+filter+"%' or rfc LIKE '%"+filter+"%' or "
                + "idEmpleado LIKE '%"+filter+"%' or numeroEmpleado LIKE '%"+filter+"%' or puesto LIKE '%"+filter+"%' or rutaFoto LIKE '%"+filter+"%' or "
                + "idUsuario LIKE '%"+filter+"%' or nombreUsuario LIKE '%"+filter+"%' or rol LIKE '%"+filter+"%');";
        
        //Generar la lista de Sucursales que se va a obtener con la consulta
        List<Empleado> empleados = new ArrayList<Empleado>();
        
        //Una variable temporal para crear nuevos objetos de tipo Empleado:
        Empleado e = null;
        
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
            e = fill(rs);
            //Agregamos el objeto de tipo Empleado a la lista dinámica:
            empleados.add(e);
        }
        
        //Cerramos los objetos de BD:
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        //Devolvemos la lista dinámica con objetos de tipo Empleado dentro:
        return empleados;
    }
    
    /**
     * Consulta y devuelve los registros de empleados encontrados.
     * 
     * Los registros encontrados se devuelven en forma de una lista dinamica
     * (List&lt;{@link Empleado}&rt;) que contiene dentro los objetos de tipo 
     * {@link Empleado}.
     * 
     * @param filtro    Es el termino de coincidencia parcial que condicionara
     * @return  Devuelve el listado de empleados encontrados 
     *          en la base de datos, en forma de una lista dinamica
     *          <code>List&lt;{@link Empleado}&rt;</code>.
     *          Si la base de datos no tiene algun registro de empleado, se 
     *          devuelve una lista vacia (NO SE DEVUELVE <code>null</code>!).
     * @throws Exception 
     */
    public List<Empleado> getAll(int estatus) throws Exception
    {
        //La consulta SQL a ejecutar:
        String sql = "SELECT * FROM v_empleados WHERE estatus="+estatus+";";
        
        //La lista dinámica donde guardaremos objetos de tipo Empleado
        //por cada registro que devuelva la BD:
        List<Empleado> empleados = new ArrayList<Empleado>();
        
        //Una variable temporal para crear nuevos objetos de tipo Empleado:
        Empleado e = null;
        
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
            e = fill(rs);
            //Agregamos el objeto de tipo Empleado a la lista dinámica:
            empleados.add(e);
        }
        
        //Cerramos los objetos de BD:
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        //Devolvemos la lista dinámica con objetos de tipo Empleado dentro:
        return empleados;
    }
    
    private Empleado fill(ResultSet rs) throws Exception
    {
        //Una variable temporal para crear nuevos objetos de tipo Empleado:
        Empleado e = new Empleado();
        
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
        e.setFoto(rs.getString("foto"));
        e.setId(rs.getInt("idEmpleado"));
        e.setNumEmpleado(rs.getString("numeroEmpleado"));           
        e.setPuesto(rs.getString("puesto"));
        e.setRutaFoto(rs.getString("rutaFoto"));
        e.setEstatus(rs.getInt("estatus"));

        //Establecemos su persona:
        e.setPersona(p);

        //Establecemos su Usuario:
        e.setUsuario(u);
        
        return e;
    }
    
    public List<Empleado> getAllSPA() throws Exception
    {
        //La consulta SQL a ejecutar:
        String sql = "SELECT * FROM v_empleados;";
        
        //La lista dinámica donde guardaremos objetos de tipo Empleado
        //por cada registro que devuelva la BD:
        List<Empleado> empleados = new ArrayList<Empleado>();
        
        //Una variable temporal para crear nuevos objetos de tipo Empleado:
        Empleado e = null;
        
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
            e = fill(rs);
            //Agregamos el objeto de tipo Empleado a la lista dinámica:
            empleados.add(e);
        }
        
        //Cerramos los objetos de BD:
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        //Devolvemos la lista dinámica con objetos de tipo Empleado dentro:
        return empleados;
    }
}
