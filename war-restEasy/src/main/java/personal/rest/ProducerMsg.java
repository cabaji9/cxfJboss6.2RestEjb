package personal.rest;

import io.swagger.annotations.Api;
import personal.rest.vo.RequestVo;
import personal.rest.vo.ResponseVo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;


/**
 * Created by HW on 10/19/16.
 */
@Api
@Path("/rest")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProducerMsg {

    private static Logger logger = Logger.getLogger(ProducerMsg.class.getName());


    @GET
    @Path("/name/{name}")
    public void doGet(@PathParam("name") String name) {
        logger.info("called rest service");

    }

    @GET
    @Path("/name")
    public Response obtainNames() {
        String name = "FUNCIONA";
        return Response.ok(name).build();
    }


    @POST
    @Path("/sent")
    public ResponseVo addMsg(RequestVo requestVo) {

        logger.info("Add msg:  "+requestVo.getRequestMsg());

        ResponseVo responseVo = new ResponseVo();
        responseVo.setMsg("WORKS");
        return responseVo;
    }


}
