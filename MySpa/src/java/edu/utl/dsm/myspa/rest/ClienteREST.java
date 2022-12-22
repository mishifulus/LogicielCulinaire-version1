
package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerCliente;
import edu.utl.dsm.myspa.controller.ControllerLogin;
import edu.utl.dsm.myspa.model.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Estudiabaantes
 */
@Path("cliente")
public class ClienteREST extends Application{
    
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("estatus") String estatus, @QueryParam ("t") String t)
    {
        ControllerLogin objCL = new ControllerLogin();
        
        String out = "";
        
        try {
            
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                //Crear el objeto del controlador
                ControllerCliente objCC = new ControllerCliente();
            
                //Llamamos el método getAll y recibimos los datos en una lista con nodos tipo cliente
                List<Cliente> clientes = new ArrayList<Cliente>();
                clientes = objCC.getAll(Integer.parseInt(estatus));
            
                //Creamos un objeto de la librería de GSON
                Gson objGS = new Gson();
           
                //Se convierte la lista JAVA de cliente en un JSON
                out = objGS.toJson(clientes);
                
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            //Poner el mismo mensaje de error por principio de usabilidad llamado CONSISTENCIA
            //En caso de error generamos una respuesta de error al servicio
            out = "{\"error\":\"Hubo un fallo en la base de datos de clientes,"
                    + "vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("insert")
    @POST
    @Produces (MediaType.APPLICATION_JSON)
    public Response insert(@FormParam("cliente") @DefaultValue("") String cliente, @FormParam("t") @DefaultValue("") String t)
    {
        ControllerLogin objCL = new ControllerLogin();
        
        String out = "";
        
        try{
            
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                //Crear un objeto de la libreria Gson
                Gson objGS = new Gson();
        
                //Se genera el objeto de tipo cliente y recibe los valores de la conversión
                Cliente c = objGS.fromJson(cliente, Cliente.class);
        
                //Crear el objeto del controlador
                ControllerCliente objCC = new ControllerCliente();
        
                //Llamamos el método insertar
                int idG = objCC.insert(c);
        
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
            out = "{\"error\":\"Hubo un fallo en la inserción del cliente,"
                    + "vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("update")
    @POST
    @Produces (MediaType.APPLICATION_JSON)
    public Response update(@FormParam("cliente") @DefaultValue("") String cliente, @FormParam("t") @DefaultValue("") String t)
    {
        ControllerLogin objCL = new ControllerLogin();
        
        String out = "";
        
        try{
            
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                //Crear un objeto de la libreria Gson
                Gson objGS = new Gson();
        
                //Se genera el objeto de tipo cliente y recibe los valores de la conversión
                Cliente c = objGS.fromJson(cliente, Cliente.class);
        
                //Crear el objeto del controlador
                ControllerCliente objCC = new ControllerCliente();
        
                //Llamamos el método insertar
                objCC.update(c);
        
                out = "{\"result\":\"La actualización del cliente resultó exitosa\"}";
                
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
            //En caso de error generamos una respuesta de error al servicio
            out = "{\"error\":\"Hubo un fallo en la actualización del cliente,"
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
                ControllerCliente objCC = new ControllerCliente();
                objCC.delete(Integer.parseInt(id));
                out = "{\"result\":\"La eliminación del cliente resultó exitosa\"}";
                
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //En caso de error generamos una respuesta de error al servicio
            out = "{\"error\":\"Hubo un fallo en la eliminación del cliente,"
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
                ControllerCliente objCC = new ControllerCliente();
            
                //Llamamos el método getAll y recibimos los datos en una lista con nodos tipo cliente
                List<Cliente> clientes = new ArrayList<Cliente>();
                clientes = objCC.search(filter);
            
                //Creamos un objeto de la librería de GSON
                Gson objGS = new Gson();
            
                //Se convierte la lista JAVA de cliente en un JSON
                out = objGS.toJson(clientes);
            
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            //Poner el mismo mensaje de error por principio de usabilidad llamado CONSISTENCIA
            //En caso de error generamos una respuesta de error al servicio
            out = "{\"error\":\"Hubo un fallo en la base de datos de clientes,"
                    + "vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
