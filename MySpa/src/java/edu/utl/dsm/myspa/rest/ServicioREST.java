package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerLogin;
import edu.utl.dsm.myspa.controller.ControllerServicio;
import edu.utl.dsm.myspa.model.Servicio;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author marti
 */

@Path("servicio")
public class ServicioREST 
{
    @Path("insert")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@FormParam("s") String s, @FormParam("t") String t) {
        ControllerLogin objCL = new ControllerLogin();
        String out = "";
        try {
            if (!t.equals("") && (objCL.validateTokenE(t))) {
                Gson objGS = new Gson();
                Servicio objS = objGS.fromJson(s, Servicio.class);

                ControllerServicio objCS = new ControllerServicio();
                int r = objCS.insert(objS);

                out = "{\"result\":\"Servicio " + r + " generado con éxito\"}";
            } else {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            out = "{\"error\":\"Se produjo un error al insertar el servicio\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
