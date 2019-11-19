package personal.rest;


//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import personal.Lookup;
import personal.ejb.TestEJBRemote;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;


/**
 * Created by HW on 10/19/16.
 */
@Slf4j
@Path("/call-ejb")
@Api(value = "call", description = "Simple rest"
)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CallEjb {

    

    private TestEJBRemote testEJBRemote;


    public CallEjb() {
        testEJBRemote= (TestEJBRemote) Lookup.getEjb("java:global/completeEar/completeEar-ejb-1.0-SNAPSHOT/TestEJBImpl!personal.ejb.TestEJBRemote");
    }

    @GET
    @Path("/name/{name}")
    public void doGet(
            @ApiParam(value = "name to add to ejb", required = true,example = "Juan")
            @PathParam("name")  String name) {
        log.info("called rest service");
        testEJBRemote.addName(name);
    }

    @GET
    @Path("/name")
   @ApiOperation(value = "Obtiene nombres", notes = "SUPER RESUMEN",response = String.class,responseContainer = "List")
    public Response obtainNames() {
        String name = testEJBRemote.obtainName();
        List<String> names = new ArrayList<>();
        names.add(name);
        names.add("hola");
        return Response.ok(names).build();
    }

}
