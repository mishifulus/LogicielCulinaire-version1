/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerLogin;
import edu.utl.dsm.myspa.controller.ControllerSucursal;
import edu.utl.dsm.myspa.model.Sucursal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
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
@Path("sucursal")
public class SucursalREST extends Application{
    
    //s={id:0,nombre:'Sucursal',domicilio:'dom',latitud:1,longitud:2,estatus:1}
    @Path("insert")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@QueryParam("s")String s,@QueryParam ("t") String t)
    {
        ControllerLogin objCL = new ControllerLogin();
        
        String out = "";
        try
        {
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                //Creamos un objeto de la libreria GSON para convertir un json en objeto de java
                Gson objGS = new Gson();
                //Pasar la "cadena" que contiene el JSON recibido a un objeto de tipo sucursal
                Sucursal suc = objGS.fromJson(s, Sucursal.class);
                //Se crea un objeto del controlador
                ControllerSucursal objCS = new ControllerSucursal();
                //Llamamos al método de insertar
                int idG = objCS.insert(suc);
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
            out = "{\"error\":\"Hubo un fallo en la inserción de la sucursal,"
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
                ControllerSucursal objCS = new ControllerSucursal();
                objCS.delete(Integer.parseInt(id));
                out = "{\"result\":\"La eliminación de la sucursal resultó exitosa\"}";
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            //En caso de error generamos una respuesta de error al servicio
            out = "{\"error\":\"Hubo un fallo en la eliminación de la sucursal,"
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
                ControllerSucursal objCS = new ControllerSucursal();
                List<Sucursal> sucursales = new ArrayList<Sucursal>();
                sucursales = objCS.getAll(Integer.parseInt(estatus));
            
                Gson objGS = new Gson();
                out = objGS.toJson(sucursales);
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            out = "{\"error\":\"Hubo un fallo en la base de datos de sucursales,"
                    + "vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    ///api/sucursal/getAll?estatus=0
    
    @Path("update")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@QueryParam("s")String s, @QueryParam ("t") String t)
    {
        ControllerLogin objCL = new ControllerLogin();
        
        String out = "";
        try
        {
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
               //Creamos un objeto de la libreria GSON para convertir un json en objeto de java
                Gson objGS = new Gson();
                //Pasar la "cadena" que contiene el JSON recibido a un objeto de tipo sucursal
                Sucursal suc = objGS.fromJson(s, Sucursal.class);
                //Creamos un controlador
                ControllerSucursal objCS = new ControllerSucursal();
                objCS.update(suc);
            
                out = "{\"reuslt\":\"La actualización de la sucursal resultó exitosa\"}"; 
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            out = "{\"error\":\"Hubo un fallo en la actualización de la sucursal,"
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
                ControllerSucursal objCS = new ControllerSucursal();
                List<Sucursal> sucursales = new ArrayList<Sucursal>();
                sucursales = objCS.search(filter);
            
                Gson objGS = new Gson();
                out = objGS.toJson(sucursales);
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            out = "{\"error\":\"No hubo ninguna coincidencia en la base de datos de sucursales,"
                    + "vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("getAllSinFiltro")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSinFiltro(){
        String out="";
        try{
            ControllerSucursal objCS = new ControllerSucursal();
            List<Sucursal> sucursales = new ArrayList<Sucursal>();
            sucursales = objCS.getAll(1);
            
            Gson objGS = new Gson();
            out = objGS.toJson(sucursales);
        }catch(Exception e){
            e.printStackTrace();
            out = "{\"error\":\"Se produjo un error al cargar el catalogo, vuelva a intentarlo\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
