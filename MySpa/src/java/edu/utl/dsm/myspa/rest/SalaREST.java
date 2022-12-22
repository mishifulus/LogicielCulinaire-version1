/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerLogin;
import edu.utl.dsm.myspa.controller.ControllerSala;
import edu.utl.dsm.myspa.model.Sala;
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

@Path("sala")
public class SalaREST extends Application{
    
    @Path("insert")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@FormParam("sala") @DefaultValue("") String sala, @FormParam("t") @DefaultValue("") String t){
        ControllerLogin objCL = new ControllerLogin();
        
        String out="";
        
        try{
            
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                Gson objGS = new Gson();
            
                Sala sal = objGS.fromJson(sala, Sala.class);
            
                ControllerSala objCS = new ControllerSala();
            
                int idG = objCS.insert(sal);
            
                out = "{\"idGenerado\":" + idG + "}";
            
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
            
            out = "{\"error\":\"Hubo un fallo en la inserción\"}";
        }
        
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@QueryParam("id") String id, @QueryParam ("t") String t){
        
        ControllerLogin objCL = new ControllerLogin();
        
        String out="";
        
        try {
            
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                ControllerSala objCS = new ControllerSala();
            
                objCS.delete(Integer.parseInt(id));
            
                out = "{\"result\":\"La eliminación resultó exitosa\"}";
            
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
            
            out = "{\"error\":\"Hubo un fallo en la eliminación\"}";
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
                ControllerSala objCS = new ControllerSala();
            
                List<Sala> salas = new ArrayList<Sala>();
                salas = objCS.getAll(Integer.parseInt(estatus));
            
                Gson objGS = new Gson();
            
                out = objGS.toJson(salas);
            
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
                ControllerSala objCS = new ControllerSala();
            
                List<Sala> salas = new ArrayList<Sala>();
                salas = objCS.search(filter);
            
                Gson objGS = new Gson();
            
                out = objGS.toJson(salas);
                
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
    
    @Path("update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@FormParam("sala") @DefaultValue("") String sala, @FormParam("t") @DefaultValue("") String t){
        
        ControllerLogin objCL = new ControllerLogin();
        
        String out="";
        
        try{
            
            if (objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                Gson objGS = new Gson();
            
                Sala sal = objGS.fromJson(sala, Sala.class);
            
                ControllerSala objCS = new ControllerSala();
            
                objCS.update(sal);
            
                out = "{\"result\":\"Modificación de la sala correcta\"}";
                
            }
            else
            {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
        }catch(Exception ex){
            ex.printStackTrace();
            
            out = "{\"error\":\"Hubo un fallo en la actualización\"}";
        }
        
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("getAllBySucursal")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBySucursal(@QueryParam("idSucursal") String idS, @QueryParam("t") String t){
        
        ControllerLogin objCL = new ControllerLogin();
        String out="";
        
        try {
            if(objCL.validateTokenE(t) || objCL.validateTokenC(t))
            {
                ControllerSala objCS = new ControllerSala();
            
                List<Sala> salas = new ArrayList<Sala>();
                salas = objCS.getAllBySucursal(Integer.parseInt(idS));
            
                Gson objGS = new Gson();
            
                out = objGS.toJson(salas); 
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
}
