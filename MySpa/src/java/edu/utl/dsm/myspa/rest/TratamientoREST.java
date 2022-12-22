
package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerLogin;
import edu.utl.dsm.myspa.controller.ControllerTratamiento;
import edu.utl.dsm.myspa.model.Tratamiento;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("tratamiento")
public class TratamientoREST extends Application {

    @Path("insert")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@QueryParam("t") String t, @QueryParam("tok") String tok) throws Exception {

        ControllerLogin objCL = new ControllerLogin();
        String out = "";

        try {
            if (objCL.validateTokenE(tok) || objCL.validateTokenC(tok)) {
                Gson objGS = new Gson();
                Tratamiento tratamiento = objGS.fromJson(t, Tratamiento.class);
                ControllerTratamiento objCP = new ControllerTratamiento();
                int idG = objCP.insert(tratamiento);
                out = "{\"idGenerado\":" + idG + "}";
            } else {
                out = "{\"error\":\"Access denied for API\"}";
            }
        } catch (Exception ex) {
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
    public Response delete(@QueryParam("id") String id, @QueryParam("tok") String tok) {
        
        ControllerLogin objCL = new ControllerLogin();
        String out = "";

        try {
            if (objCL.validateTokenE(tok) || objCL.validateTokenC(tok)) {
                ControllerTratamiento objCT = new ControllerTratamiento();
                objCT.delete(Integer.parseInt(id));
                out = "{\"result\":\"La eliminación del producto resultó exitosa\"}";
            } else {
                out = "{\"error\":\"Access denied for API\"}";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //In case of error we generate an error response to the service
            out = "{\"error\":\"There was a failure to remove the treatment,"
                    + "Try again or call your system administrator\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("estatus") String estatus, @QueryParam("tok") String tok) {
        
        ControllerLogin objCL = new ControllerLogin();
        String out = "";

        try {
            if (objCL.validateTokenE(tok) || objCL.validateTokenC(tok)) {
                ControllerTratamiento objCT = new ControllerTratamiento();
                List<Tratamiento> tratamiento = new ArrayList<Tratamiento>();
                tratamiento = objCT.getAll(Integer.parseInt(estatus));

                Gson objGS = new Gson();
                out = objGS.toJson(tratamiento);
            } else {
                out = "{\"error\":\"Access denied for API\"}";
            }

        } catch (Exception ex) {

            ex.printStackTrace();
            out = "{\"error\":\"There was a failure in the creation of the table, "
                    + "try again or call your system administrator\"}";

        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("filter") String filter, @QueryParam("tok") String tok) {

        ControllerLogin objCL = new ControllerLogin();
        String out = "";

        try {

            if (objCL.validateTokenE(tok) || objCL.validateTokenC(tok)) {
                ControllerTratamiento objCT = new ControllerTratamiento();
                List<Tratamiento> tratamiento = new ArrayList<Tratamiento>();
                tratamiento = objCT.search(filter);

                Gson objGS = new Gson();
                out = objGS.toJson(tratamiento);
            } else {
                out = "{\"error\":\"Access denied for API\"}";
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            out = "{\"error\":\"An error occurred while loading the catalog, please try again\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("update")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@QueryParam("t") String t, @QueryParam("tok") String tok) {
        
        ControllerLogin objCL = new ControllerLogin();
        String out = "";
        
        try {
            if (objCL.validateTokenE(tok) || objCL.validateTokenC(tok)) {

                Gson objGS = new Gson();
                Tratamiento tratamiento = objGS.fromJson(t, Tratamiento.class);
                ControllerTratamiento objCT = new ControllerTratamiento();
                objCT.update(tratamiento);

                out = "{\"reuslt\":\"La actualización del producto resultó exitosa\"}";
            } else {
                out = "{\"error\":\"Access denied for API\"}";
            }

        } catch (Exception ex) {

            ex.printStackTrace();
            out = "{\"error\": \"Hubo un fallo en la actualización de datos,"
                    + "vuelve a intentarlo, o llama al administrador del sistema\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
