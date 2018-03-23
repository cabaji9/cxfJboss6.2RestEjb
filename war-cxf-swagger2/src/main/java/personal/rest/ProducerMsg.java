package personal.rest;


import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import io.swagger.annotations.*;
import io.swagger.oas.annotations.Operation;
import io.swagger.oas.annotations.media.Content;
import io.swagger.oas.annotations.media.Schema;
import io.swagger.oas.annotations.responses.ApiResponse;
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
import java.util.ArrayList;
import java.util.List;


/**
 * Created by HW on 10/19/16.
 */
@Path("/rest")
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

            @PathParam("name")@NotNull @Size(max = 20)  String name) {
        logger.info("called rest service");
        testEJBRemote.addName(name);
    }

    @GET
    @Path("/name")
    @Operation(summary = "Get users",
            description = "Get list of users")
    public Response obtainNames() {
        String name = testEJBRemote.obtainName();
        List<String> names = new ArrayList<>();
        names.add(name);
        names.add("hola");
        return Response.ok(names).build();
    }


    @POST
    @Path("/sent")
    @Operation(summary = "Get user by user name",
            responses = {
                    @ApiResponse(description = "The user",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseVo.class))),
                    @ApiResponse(responseCode = "400", description = "User not found")})
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
