package personal.rest;


import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import personal.Lookup;
import personal.ejb.TestEJBRemote;
import personal.vo.ProducerResponseVo;
import personal.vo.RequestVo;
import personal.vo.ResponseVo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Created by HW on 10/19/16.
 */
@Path("/rest")
@Api(value = "rest", description = "Simple rest"
)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProducerMsg {

    private static Logger logger = LoggerFactory.getLogger(ProducerMsg.class);


    private TestEJBRemote testEJBRemote;


    public ProducerMsg() {
         testEJBRemote= (TestEJBRemote) Lookup.getEjb("java:global/completeEar/completeEar-ejb-1.0-SNAPSHOT/TestEJBImpl!personal.ejb.TestEJBRemote");
    }


    @GET
    @Path("/name/{name}")
    public void doGet(
            @ApiParam(value = "name to add to ejb", required = true,example = "Juan")
            @PathParam("name")@NotNull @Size(max = 20)  String name) {
        logger.info("called rest service");
        testEJBRemote.addName(name);
    }

    @GET
    @Path("/name")
    @ApiOperation(value = "Obtiene nombres", notes = "SUPER RESUMEN",response = String.class)
    public Response obtainNames() {
        String name = testEJBRemote.obtainName();
        return Response.ok(name).build();
    }


    @POST
    @Path("/sent")
    @ApiOperation(value = "Envia un parametro y lo verifica", notes = "SUPER RESUMEN")
    @ApiResponses(value = {
            @ApiResponse(response = ResponseVo.class,code = 400, message = "Sent wrong parameter fail"),
            @ApiResponse(response = ProducerResponseVo.class,code = 200, message = "Ok everything works") })
    public Response addMsg(@Valid RequestVo requestVo) {
        logger.info("addMsg | Entered with {}",requestVo);

        logger.info("Add msg:  "+requestVo.getRequestMsg());
        Response response;
        ResponseVo responseVo = new ResponseVo();
        responseVo.setErrorMessage(requestVo.getRequestMsg());
        if(requestVo.getRequestMsg().equals("falla")){
            response = obtainErrorResponse("FAILED! ");
        }
        else{
            response = obtainSuccessResponse("SUCCESS : "+requestVo.getRequestMsg());
        }
        return response;
    }


    private Response obtainErrorResponse(String msg){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setErrorMessage(msg);
        return  Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(responseVo).build();
    }

    private Response obtainSuccessResponse(String msg){
        ProducerResponseVo producerResponseVo = new ProducerResponseVo();
        producerResponseVo.setMessage(msg);
        return Response.ok().entity(producerResponseVo).build();
    }


}
