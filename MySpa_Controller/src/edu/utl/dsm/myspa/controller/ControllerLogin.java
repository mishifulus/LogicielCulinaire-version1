
package edu.utl.dsm.myspa.controller;

import edu.utl.dsm.myspa.db.ConexionMySQL;
import edu.utl.dsm.myspa.model.Cliente;
import edu.utl.dsm.myspa.model.Empleado;
import edu.utl.dsm.myspa.model.Persona;
import edu.utl.dsm.myspa.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ControllerLogin {
    
    public Empleado login(String nombreU, String contra) throws Exception
    {
        //Definir la consulta
        String query = "SELECT * FROM v_empleados WHERE nombreUsuario = ? AND contrasenia = ? AND estatus = 1 AND (token IS NULL OR token = '');";
        
        //Generar la conexión
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        //Abrir la conexión
        Connection conn = connMySQL.open();
        
        //Objeto para ejecutar los parametros de la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        //Llenar los parametros de la consulta
        pstmt.setString(1, nombreU);
        pstmt.setString(2, contra);
        
        //Objeto para recibir el resultadoo
        ResultSet rs = pstmt.executeQuery();
        
        //Objeto de tipo empleado
        Empleado e = null;
        
        if (rs.next())
        {
            e = fill(rs);
            saveToken(e.getUsuario());
        }
        
        //Cerrar los objetos de uso para la bd
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        //Se devuelve el objeto empleado
        return e;
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
        u.setToken();

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
    
    
    //CLIENTE
    public Cliente loginC(String nombreU, String contra) throws Exception
    {
        //Definir la consulta
        String query = "SELECT * FROM v_clientes WHERE nombreUsuario = ? AND contrasenia = ? AND estatus = 1 AND (token IS NULL OR token = '');";
        
        //Generar la conexión
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        //Abrir la conexión
        Connection conn = connMySQL.open();
        
        //Objeto para ejecutar los parametros de la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        //Llenar los parametros de la consulta
        pstmt.setString(1, nombreU);
        pstmt.setString(2, contra);
        
        //Objeto para recibir el resultadoo
        ResultSet rs = pstmt.executeQuery();
        
        //Objeto de tipo empleado
        Cliente c = null;
        
        if (rs.next())
        {
            c = fillC(rs);
            saveToken(c.getUsuario());
        }
        
        //Cerrar los objetos de uso para la bd
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        //Se devuelve el objeto empleado
        return c;
    }
    
    private Cliente fillC(ResultSet rs) throws Exception
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
        u.setToken();

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
    
    public void saveToken(Usuario u) throws Exception
    {
        //Generamos la consulta
        String query = "UPDATE usuario SET token='"+u.getToken()+"' WHERE idUsuario="+u.getId()+";";
        //Generar el objeto de la conexión
        ConexionMySQL connMySQL = new ConexionMySQL();
        //Abrir la conexión
        Connection conn = connMySQL.open();
        //Objeto para ejecutar la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.execute();
        pstmt.close();
        conn.close();
        connMySQL.close();
    }
    
    public void deleteToken(Usuario u) throws Exception
    {
        //Generamos la consulta
        String query = "UPDATE usuario SET token = '' WHERE idUsuario = "+u.getId()+";";
        //Generar el objeto de la conexión
        ConexionMySQL connMySQL = new ConexionMySQL();
        //Abrir la conexión
        Connection conn = connMySQL.open();
        //Objeto paara ejecutar la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.execute();
        pstmt.close();
        conn.close();
        connMySQL.close();
    }
    
    public boolean validateTokenE(String token) throws Exception
    {
        boolean valid = false;
        
        //Generamos la consulta
        String query = "SELECT * FROM v_empleados WHERE token = '"+token+"';";
        //Generar el objeto de la conexión
        ConexionMySQL connMySQL = new ConexionMySQL();
        //Abrir la conexión
        Connection conn = connMySQL.open();
        //Objeto para ejecutar la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next())
        {
            valid = true;
        }
        pstmt.close();
        conn.close();
        connMySQL.close();
        
        return valid;
    }
    
    public boolean validateTokenC(String token) throws Exception
    {
        boolean valid = false;
        
        //Generamos la consulta
        String query = "SELECT * FROM v_clientes WHERE token = '"+token+"';";
        //Generar el objeto de la conexión
        ConexionMySQL connMySQL = new ConexionMySQL();
        //Abrir la conexión
        Connection conn = connMySQL.open();
        //Objeto para ejecutar la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next())
        {
            valid = true;
        }
        pstmt.close();
        conn.close();
        connMySQL.close();
        
        return valid;
    }
}
