/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerLogin;
import edu.utl.dsm.myspa.controller.ControllerProducto;
import edu.utl.dsm.myspa.model.Producto;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("producto")
public class ProductoREST extends Application{
    
    @Path("insert")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@QueryParam("p")String p, @QueryParam ("t") String t)
    {
        ControllerLogin objCL = new ControllerLogin();
        
        String out = "";
        try
        {
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                //Creamos un objeto de la libreria GSON para convertir un json en objeto de java
                Gson objGS = new Gson();
                //Pasar la "cadena" que contiene el JSON recibido a un objeto de tipo producto
                Producto pro = objGS.fromJson(p, Producto.class);
                //Se crea un objeto del controlador
                ControllerProducto objCP = new ControllerProducto();
                //Llamamos al método de insertar
                int idG = objCP.insert(pro);
                //Generamos la respuesta del servicio que contenga el id que generó la inserción
                out = "{\"idGenerado\":"+idG+"}";  
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            //En caso de error generamos una respuesta de error al servicio
            out = "{\"error\":\"Hubo un fallo en la inserción del producto,"
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
                ControllerProducto objCP = new ControllerProducto();
                objCP.delete(Integer.parseInt(id));
                out = "{\"result\":\"La eliminación del producto resultó exitosa\"}";
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            //En caso de error generamos una respuesta de error al servicio
            out = "{\"error\":\"Hubo un fallo en la eliminación del producto,"
                    + "vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("estatus") String estatus, @QueryParam ("t") String t)
    {
        ControllerLogin objCL = new ControllerLogin();
        
        String out = "";
        try
        {
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                ControllerProducto objCP = new ControllerProducto();
                List<Producto> productos = new ArrayList<Producto>();
                productos = objCP.getAll(Integer.parseInt(estatus));
            
                Gson objGS = new Gson();
                out = objGS.toJson(productos);
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            out = "{\"error\":\"Hubo un fallo en la base de datos de productos,"
                    + "vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("update")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@QueryParam("p")String p, @QueryParam ("t") String t)
    {
        ControllerLogin objCL = new ControllerLogin();
        
        String out = "";
        try
        {
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                //Creamos un objeto de la libreria GSON para convertir un json en objeto de java
                Gson objGS = new Gson();
                //Pasar la "cadena" que contiene el JSON recibido a un objeto de tipo producto
                Producto pro = objGS.fromJson(p, Producto.class);
                //Creamos un controlador
                ControllerProducto objCP = new ControllerProducto();
                objCP.update(pro);
            
                out = "{\"reuslt\":\"La actualización del producto resultó exitosa\"}";
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            out = "{\"error\":\"Hubo un fallo en la actualización del producto,"
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
        try
        {
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                ControllerProducto objCP = new ControllerProducto();
                List<Producto> productos = new ArrayList<Producto>();
                productos = objCP.search(filter);
            
                Gson objGS = new Gson();
                out = objGS.toJson(productos);
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            out = "{\"error\":\"No hubo ninguna coincidencia en la base de datos de productos,"
                    + "vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
