
package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerLogin;
import edu.utl.dsm.myspa.model.Cliente;
import edu.utl.dsm.myspa.model.Empleado;
import edu.utl.dsm.myspa.model.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("log")
public class LoginREST extends Application{
    
    /**
     *
     * @param nU
     * @param c
     * @return
     */
    @Path("in")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response in(@FormParam("nU") @DefaultValue("0") String nU, @FormParam("c") @DefaultValue("0") String c)
    {
        String out = "";
        try
        {
            ControllerLogin objCL = new ControllerLogin();
            Empleado e = objCL.login(nU,c);
            
            Gson objGS = new Gson();
            out = objGS.toJson(e);
            
        } catch(Exception ex)
        {
            ex.printStackTrace();
            out = "{\"error\":\"Hubo un fallo al iniciar sesión,"
                    + "revise los datos ingresados\"}";
        }
        
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("inC")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response inC(@FormParam("nU") @DefaultValue("0") String nU, @FormParam("c") @DefaultValue("0") String c)
    {
        String out = "";
        try
        {
            ControllerLogin objCL = new ControllerLogin();
            Cliente cl = objCL.loginC(nU,c);
            
            Gson objGS = new Gson();
            out = objGS.toJson(cl);
            
        } catch(Exception ex)
        {
            ex.printStackTrace();
            out = "{\"error\":\"Hubo un fallo al iniciar sesión,"
                    + "revise los datos ingresados\"}";
        }
        
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("out")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response out (@FormParam("idU") @DefaultValue("0") String idU)
    {
        String out = "";
        try {
            Usuario objU = new Usuario();
            objU.setId(Integer.parseInt(idU));
            ControllerLogin objCL = new ControllerLogin();
            objCL.deleteToken(objU);
            
            out = "{\"result\":\"Ok\"}";
            
        } catch (Exception ex) {
            ex.printStackTrace();
            out = "{\"error\":\"Se generó un error en el cierre de sesión\"}";
        }
        
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
