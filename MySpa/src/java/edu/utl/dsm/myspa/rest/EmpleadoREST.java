
package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerEmpleado;
import edu.utl.dsm.myspa.controller.ControllerLogin;
import edu.utl.dsm.myspa.model.Empleado;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author marti
 */
@Path("empleado")
public class EmpleadoREST extends Application
{
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("e") String e, @QueryParam ("t") String t)
    {
        ControllerLogin objCL = new ControllerLogin();
        
        String out = "";
        
        try {
            
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                //Crear el objeto del controlador
            ControllerEmpleado objCE = new ControllerEmpleado();
            
            //Llamamos el método getAll y recibimos los datos en una lista con nodos tipo empleado
            List<Empleado> empleados = new ArrayList<Empleado>();
            empleados = objCE.getAll(Integer.parseInt(e));
            
            //Creamos un objeto de la librería de GSON
            Gson objGS = new Gson();
            
            //Se convierte la lista JAVA de empleado en un JSON
            out = objGS.toJson(empleados);
            
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            //Poner el mismo mensaje de error por principio de usabilidad llamado CONSISTENCIA
            //En caso de error generamos una respuesta de error al servicio
            out = "{\"error\":\"Hubo un fallo en la base de datos de empleados,"
                    + "vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    
    @Path("insert")
    @POST
    @Produces (MediaType.APPLICATION_JSON)
    public Response insert(@FormParam("empleado") @DefaultValue("") String empleado, @FormParam("t") @DefaultValue("") String t)
    {
        ControllerLogin objCL = new ControllerLogin();
        
        String out = "";
        
        try{
            
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                //Crear un objeto de la libreria Gson
                Gson objGS = new Gson();
        
                //Se genera el objeto de tipo empleado y recibe los valores de la conversión
                Empleado e = objGS.fromJson(empleado, Empleado.class);
        
                //Crear el objeto del controlador
                ControllerEmpleado objCE = new ControllerEmpleado();
        
                //Llamamos el método insertar
                int idG = objCE.insert(e);
        
                //Generamos la respuesta del servicio que contenga el id que generó la inserción
                out = "{\"idGenerado\":"+idG+"}";
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
            //En caso de error generamos una respuesta de error al servicio
            out = "{\"error\":\"Hubo un fallo en la inserción del empleado,"
                    + "vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("update")
    @POST
    @Produces (MediaType.APPLICATION_JSON)
    public Response update(@FormParam("empleado") @DefaultValue("") String empleado, @FormParam("t") @DefaultValue("") String t)
    {
        ControllerLogin objCL = new ControllerLogin();
        
        String out = "";
        
        try{
            
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                //Crear un objeto de la libreria Gson
                Gson objGS = new Gson();
        
                //Se genera el objeto de tipo empleado y recibe los valores de la conversión
                Empleado e = objGS.fromJson(empleado, Empleado.class);
        
                //Crear el objeto del controlador
                ControllerEmpleado objCE = new ControllerEmpleado();
        
                //Llamamos el método insertar
                objCE.update(e);
        
                out = "{\"result\":\"La actualización del empleado resultó exitosa\"}";
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
            //En caso de error generamos una respuesta de error al servicio
            out = "{\"error\":\"Hubo un fallo en la actualización del empleado,"
                    + "vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete (@QueryParam("id") String id, @QueryParam ("t") String t)
    {
        ControllerLogin objCL = new ControllerLogin();
        
        String out = "";
        try {
            
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                ControllerEmpleado objCE = new ControllerEmpleado();
                objCE.delete(Integer.parseInt(id));
                out = "{\"result\":\"La eliminación del empleado resultó exitosa\"}";
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            //En caso de error generamos una respuesta de error al servicio
            out = "{\"error\":\"Hubo un fallo en la eliminación del empleado,"
                    + "vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("filter") String filter, @QueryParam ("t") String t)
    {
        ControllerLogin objCL = new ControllerLogin();
        
        String out = "";
        
        try {
            
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                //Crear el objeto del controlador
                ControllerEmpleado objCE = new ControllerEmpleado();
            
                //Llamamos el método getAll y recibimos los datos en una lista con nodos tipo empleado
                List<Empleado> empleados = new ArrayList<Empleado>();
                empleados = objCE.search(filter);
            
                //Creamos un objeto de la librería de GSON
                Gson objGS = new Gson();
            
                //Se convierte la lista JAVA de empleado en un JSON
                out = objGS.toJson(empleados);
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            //Poner el mismo mensaje de error por principio de usabilidad llamado CONSISTENCIA
            //En caso de error generamos una respuesta de error al servicio
            out = "{\"error\":\"Hubo un fallo en la base de datos de empleados,"
                    + "vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("getAllSPA")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSPA()
    {
        
        String out = "";
        
        try {
                //Crear el objeto del controlador
                ControllerEmpleado objCE = new ControllerEmpleado();
            
                //Llamamos el método getAll y recibimos los datos en una lista con nodos tipo empleado
                List<Empleado> empleados = new ArrayList<Empleado>();
                empleados = objCE.getAllSPA();
           
                //Creamos un objeto de la librería de GSON
                Gson objGS = new Gson();
            
                //Se convierte la lista JAVA de empleado en un JSON
                out = objGS.toJson(empleados);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            //Poner el mismo mensaje de error por principio de usabilidad llamado CONSISTENCIA
            //En caso de error generamos una respuesta de error al servicio
            out = "{\"error\":\"Hubo un fallo en la base de datos de empleados,"
                    + "vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
