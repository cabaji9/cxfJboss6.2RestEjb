package personal.rest;


import personal.ejb.TestEJBRemote;
import personal.rest.vo.RequestVo;
import personal.rest.vo.ResponseVo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;


/**
 * Created by HW on 10/19/16.
 */
@Path("/rest")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProducerMsg {

    private static Logger logger = Logger.getLogger(ProducerMsg.class.getName());


    private TestEJBRemote testEJBRemote;


    public ProducerMsg() {
        // testEJBRemote= (TestEJBRemote) Lookup.getEjb("java:global/completeEar/completeEar-ejb-1.0-SNAPSHOT/TestEJBImpl!personal.ejb.TestEJBRemote");
    }


    @GET
    @Path("/name/{name}")
    public void doGet(@PathParam("name") String name) {
        logger.info("called rest service");
        testEJBRemote.addName(name);
    }

    @GET
    @Path("/name")
    public Response obtainNames() {
        String name = testEJBRemote.obtainName();
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
