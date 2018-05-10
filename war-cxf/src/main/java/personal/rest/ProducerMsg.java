package personal.rest;


import io.swagger.annotations.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import personal.Lookup;
import personal.ejb.TestEJBRemote;
import personal.mock.base.Base;
import personal.mock.base.entity.FileData;
import personal.vo.ProducerResponseVo;
import personal.vo.RequestVo;
import personal.vo.ResponseVo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.*;


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
    private Base base = Base.getInstance();


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
    @ApiOperation(value = "Obtiene nombres", notes = "SUPER RESUMEN",response = String.class,responseContainer = "List")
    public Response obtainNames() {
        String name = testEJBRemote.obtainName();
        List<String> names = new ArrayList<>();
        names.add(name);
        names.add("hola");
        return Response.ok(names).build();
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



    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @ApiImplicitParams({
            @ApiImplicitParam(name="data", value = "Request vo Json Añadir el archivo con el .json",
                    dataType = "java.io.File", paramType = "form"),
            @ApiImplicitParam(name="upfile", value = "file",
                    required = true, dataType = "java.io.File", paramType = "form")})
    public Response uploadFile(@Multipart(value = "data", type = "application/json") RequestVo requestVo,
                               @Multipart("upfile") Attachment attachment) throws Exception{

        String filename = attachment.getContentDisposition().getParameter("filename");

        String contentType =attachment.getContentType().toString();

        logger.info("filename : {}",filename);
        logger.info("contentType : {}",contentType);

        java.nio.file.Path path = Paths.get("/Users/User/Downloads/test/" + filename);
        InputStream in = attachment.getObject(InputStream.class);
        Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
        logger.info("requestVo :{}",requestVo);
        in.close();
        return obtainSuccessResponse("SUCCESS FILE UPLOADED : "+requestVo.getRequestMsg());

    }




    @POST
    @Path("/upload-mock-base")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @ApiImplicitParams({
            @ApiImplicitParam(name="data", value = "Request vo Json Añadir el archivo con el .json",
                    dataType = "java.io.File", paramType = "form"),
            @ApiImplicitParam(name="upfile", value = "file",
                    required = true, dataType = "java.io.File", paramType = "form")})
    public Response uploadFileMockBase(@Multipart(value = "data", type = "application/json") RequestVo requestVo,
                               @Multipart("upfile") Attachment attachment) throws Exception{

        logger.info("requestVo :{}",requestVo);
        Integer id =saveFileData(attachment);

        return obtainSuccessResponse("SUCCESS FILE UPLOADED {0} with id {1} "+requestVo.getRequestMsg(),id+"",id);

    }


    @GET
    @Path("/obtain-file-base/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the file") })
    public Response getFileDataBase(@PathParam("id") Integer id){
        Response response;
        FileData fileData =base.get(id);
        if(fileData == null){
            response = obtainErrorResponse("FAILED! ");
        }
        else{
            response =  Response.ok().entity(fileData.getFile())
                    .header("Content-Disposition", "attachment; filename=\"" + fileData.getFileName() + "\"" ) .build();
        }
        return response;
    }


    private Integer saveFileData(Attachment attachment) throws Exception{
        String filename = attachment.getContentDisposition().getParameter("filename");
        String contentType =attachment.getContentType().toString();

        Integer id = base.getId();
        logger.info("filename : {}",filename);
        logger.info("contentType : {}",contentType);
        InputStream in = attachment.getObject(InputStream.class);



        FileData fileData = new FileData();
        fileData.setFileName(filename);
        fileData.setContentType(contentType);
        fileData.setFile(org.apache.commons.io.IOUtils.toByteArray(in));

        logger.info("filedata {}",fileData);

        base.put(id,fileData);

        in.close();
        return id;
    }


    @GET
    @Path("/obtain-file-base64")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok everything works") })
    public Response getFileBase64() throws Exception{

        InputStream in = getClass().getClassLoader().getResourceAsStream("image.jpeg");

        ProducerResponseVo producerResponseVo =new ProducerResponseVo();
        producerResponseVo.setMessage("FILA!!");
        producerResponseVo.setImage(obtainImageOnBase64(in));
        return Response.ok().entity(producerResponseVo).build();

    }


    private String obtainImageOnBase64(InputStream in) throws Exception{
        byte[] bytes =  org.apache.commons.io.IOUtils.toByteArray(in);
        return  Base64.encodeBase64String(bytes);
    }


    @GET
    @Path("/obtain-file")
    @Produces(MediaType.MULTIPART_FORM_DATA)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok everything works, Returns a mulipart/form -> Swagger dont know how to parse this. Use a client like postman") })
    public MultipartBody getFileData(){

        InputStream in = getClass().getClassLoader().getResourceAsStream("image.jpeg");

        ProducerResponseVo producerResponseVo =new ProducerResponseVo();
        producerResponseVo.setMessage("FILA!!");

        List<Attachment> atts = new LinkedList<>();
        atts.add(new Attachment("responseVo", "application/json",producerResponseVo));
        atts.add(new Attachment("image", "application/octet-stream", in));
        return new MultipartBody(atts, true);

    }



    @GET
    @Path("/obtain-file-map")
    @Produces("multipart/mixed")
    public Map<String, Object> getFileDataMap() {

        InputStream in = getClass().getClassLoader().getResourceAsStream("image.jpeg");

        ProducerResponseVo producerResponseVo = new ProducerResponseVo();
        producerResponseVo.setMessage("FILA MAP!!");

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("application/json", producerResponseVo);
        map.put("application/octet-stream", in);
        return map;
    }


    private Response obtainErrorResponse(String msg){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setErrorMessage(msg);
        return  Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(responseVo).build();
    }

    private Response obtainSuccessResponse(String msg,Object... vars){
        ProducerResponseVo producerResponseVo = new ProducerResponseVo();
        producerResponseVo.setMessage(MessageFormat.format(msg,vars));
        return Response.ok().entity(producerResponseVo).build();
    }

    private Response obtainSuccessResponse(String msg,String id,Object... vars){
        ProducerResponseVo producerResponseVo = new ProducerResponseVo();
        producerResponseVo.setImage(id+"");
        producerResponseVo.setMessage(MessageFormat.format(msg,vars));
        return Response.ok().entity(producerResponseVo).build();
    }


}
