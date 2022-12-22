/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerHorario;
import edu.utl.dsm.myspa.controller.ControllerLogin;
import edu.utl.dsm.myspa.model.Horario;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("horario")
public class HorarioREST extends Application
{
    
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam ("t") String t) {
        
        ControllerLogin objCL = new ControllerLogin();
        
        String out = "";
        try {
            
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                // Se crea un objeto del controlador
                ControllerHorario objCH = new ControllerHorario();
                List<Horario> horarios= new ArrayList<Horario>();
                horarios= objCH.getAll();
                Gson objGS = new Gson();
                out = objGS.toJson(horarios);
                
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // En caso de algun error generamos la respuestade error del servicio
            out = "{\"error\":\"Hubo un fallo al cargar el catalogo de horarios, "
                    + "vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("insert")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@QueryParam("h") String h, @QueryParam ("t") String t) {
        
        ControllerLogin objCL = new ControllerLogin();
        
        String out = "";
        try {
            
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                // Creamos un objeto de la libreria Gson para convertir un json en objeo de java
                Gson objGS = new Gson();
                // Pasar la cadena que contiene el json recibido a un objeto del tipo horario
                Horario hor = objGS.fromJson(h, Horario.class);
                // Se crea un objeto del controlador
                ControllerHorario objCH = new ControllerHorario();
                // Llamamos al metodo de insertar
                int idG = objCH.insert(hor);
                // Generamos la respuesta del servicio que contenga el id que genero la inserccion
                out = "{\"idGenerado\":" + idG + "}";
            
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            // En caso de algun error generamos la respuestade error del servicio
            out = "{\"error\":\"Hubo un fallo en la inserccion del horario, "
                    + "vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("update")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@QueryParam("h") String h, @QueryParam ("t") String t) {
        
        ControllerLogin objCL = new ControllerLogin();
        
        String out = "";
        try {
            
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                // Creamos un objeto de la libreria Gson para convertir un json en objeo de java
                Gson objGS = new Gson();
                // Pasar la cadena que contiene el json recibido a un objeto del tipo sucursal
                Horario hor = objGS.fromJson(h, Horario.class);
                // Se crea un objeto del controlador
                ControllerHorario objCH = new ControllerHorario();
                // Llamamos al metodo de insertar
                objCH.update(hor);
                // Generamos la respuesta del servicio que contenga el id que genero la inserccion
                out = "{\"result\":\"La modificacion resulto exitosa\"}";
                
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            // En caso de algun error generamos la respuestade error del servicio
            out = "{\"error\":\"Hubo un fallo en la modificacion del horario, "
                    + "vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@QueryParam("id") String id, @QueryParam ("t") String t) {
        
        ControllerLogin objCL = new ControllerLogin();
        
        String out = "";
        try {
            
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                // Se crea un objeto del controlador
                ControllerHorario objCH = new ControllerHorario();
                // Invocamos el metodo de eliminar
                objCH.delete(Integer.parseInt(id));
                out = "{\"result\":\"La eliminacion resulto exitosa\"}";
                
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // En caso de algun error generamos la respuestade error del servicio
            out = "{\"error\":\"Hubo un fallo en la eliminacion del horario, "
                    + "vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("filter") String filter, @QueryParam ("t") String t) {
        
        ControllerLogin objCL = new ControllerLogin();
        
        String out = "";
        try {
            
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                // Se crea un objeto del controlador
                ControllerHorario objCH = new ControllerHorario();            
                List<Horario> horarios= new ArrayList<Horario>();
                // Invocamos el metodo de eliminar
                horarios=objCH.search(filter);
                Gson objGS = new Gson();
                out = objGS.toJson(horarios);
                
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // En caso de algun error generamos la respuestade error del servicio
            out = "{\"error\":\"Hubo un fallo en la busqueda del horario, "
                    + "vuelve a intentarlo o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
