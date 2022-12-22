/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerLogin;
import edu.utl.dsm.myspa.controller.ControllerReservacion;
import edu.utl.dsm.myspa.model.Cliente;
import edu.utl.dsm.myspa.model.Horario;
import edu.utl.dsm.myspa.model.Reservacion;
import edu.utl.dsm.myspa.model.Sala;
import java.util.ArrayList;
import java.util.List;
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
@Path("reservacion")
public class ReservacionREST extends Application{
    
    @Path("getHourAv")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHourAv(@QueryParam("fecha") String fecha,
                              @QueryParam("sala") String sala,
                              @QueryParam("t") String t)
    {
        ControllerLogin objCL = new ControllerLogin();
        
        String out = "";
        
        try
        {
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                ControllerReservacion objCR = new ControllerReservacion();
                List<Horario> horarios = objCR.getHourAv(fecha, Integer.parseInt(sala));
                Gson objGS = new Gson();
                out = objGS.toJson(horarios);
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
            out = "{\"error\":\"Se produjo un error al cargar las horas disponibles\"}";
        }
        
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("insert")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@QueryParam("estatus") String estatus,
                           @QueryParam("cliente") String cliente,
                           @QueryParam("fecha") String fecha,
                           @QueryParam("sala") String sala,
                           @QueryParam("horario") String horario,
                           @QueryParam("t") String t)
    {
        ControllerLogin objCL = new ControllerLogin();
        
        String out = "";
        
        try
        {
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                Cliente objC = new Cliente();
                objC.setId(Integer.parseInt(cliente));
                
                Sala objS = new Sala();
                objS.setId(Integer.parseInt(sala));
                
                Horario objH = new Horario();
                objH.setId(Integer.parseInt(horario));
                
                Reservacion objR = new Reservacion();
                objR.setCliente(objC);
                objR.setEstatus(Integer.parseInt(estatus));
                objR.setFecha(fecha);
                objR.setHorario(objH);
                objR.setSala(objS);
                
                ControllerReservacion objCR = new ControllerReservacion();
                objCR.insert(objR);
                
                out = "{\"result\":\"Reservación almacenada con éxito\"}";
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
            out = "{\"error\":\"Se produjo un error al insertar la reservación\"}";
        }
        
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("cancel")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancel(@QueryParam("id") String id, @QueryParam ("t") String t){
        
        ControllerLogin objCL = new ControllerLogin();
        
        String out="";
        
        try {
            
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                ControllerReservacion objCR = new ControllerReservacion();
            
                objCR.cancel(Integer.parseInt(id));
            
                out = "{\"result\":\"La cancelación resultó exitosa\"}";
            
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
            
            out = "{\"error\":\"Hubo un fallo en lacancelación\"}";
        }
        
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("estatus") String estatus, @QueryParam ("t") String t){
        
        ControllerLogin objCL = new ControllerLogin();
        
        String out="";
        
        try {
            
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                ControllerReservacion objCR = new ControllerReservacion();
            
                List<Reservacion> reservaciones = new ArrayList<Reservacion>();
                reservaciones = objCR.getAll(Integer.parseInt(estatus));
            
                Gson objGS = new Gson();
            
                out = objGS.toJson(reservaciones);
            
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            
            out = "{\"error\":\"Se produjo un error al cargar el catalogo, vuelva a intentarlo\"}";
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
                ControllerReservacion objCR = new ControllerReservacion();
            
                List<Reservacion> reservaciones = new ArrayList<Reservacion>();
                reservaciones = objCR.search(filter);
            
                Gson objGS = new Gson();
            
                out = objGS.toJson(reservaciones);
                
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
        }catch (Exception ex){
            ex.printStackTrace();
            out = "{\"error\":\"Se produjo un error al cargar el catalogo, vuelva a intentarlo\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
