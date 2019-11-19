package personal.rest;


import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.jboss.resteasy.plugins.providers.multipart.MultipartOutput;
import personal.mock.base.Base;
import personal.mock.base.entity.FileData;
import personal.vo.ProducerResponseVo;
import personal.vo.RequestVo;
import personal.vo.ResponseVo;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by HW on 10/19/16.
 */
@Slf4j
@Path("/rest")
@Api(value = "rest", description = "Simple rest")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProducerMsg {


    private Base base = Base.getInstance();


    public ProducerMsg() {
        base = Base.getInstance();
    }


    @POST
    @Path("/sent")
    @ApiOperation(value = "Envia un parametro y lo verifica", notes = "SUPER RESUMEN")
    @ApiResponses(value = {
            @ApiResponse(response = ResponseVo.class, code = 400, message = "Sent wrong parameter fail"),
            @ApiResponse(response = ProducerResponseVo.class, code = 200, message = "Ok everything works")})
    public Response addMsg(@Valid RequestVo requestVo) {
        log.info("addMsg | Entered with {}", requestVo);

        log.info("Add msg:  " + requestVo.getRequestMsg());
        Response response;
        if (requestVo.getRequestMsg().equals("falla")) {
            response = obtainErrorResponse("FAILED! ");
        } else {
            response = obtainSuccessResponse("SUCCESS : " + requestVo.getRequestMsg());
        }
        return response;
    }


    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "Request vo Json Añadir el archivo con el .json",
                    dataType = "java.io.File", paramType = "form"),
            @ApiImplicitParam(name = "upfile", value = "file",
                    required = true, dataType = "java.io.File", paramType = "form")})
    public Response uploadFile(MultipartFormDataInput input) throws Exception {


        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        RequestVo requestVo = uploadForm.get("data").get(0).getBody(RequestVo.class, RequestVo.class);
        InputPart inputPartFile = uploadForm.get("upfile").get(0);
        InputStream inputStream = inputPartFile.getBody(InputStream.class, null);

        MultivaluedMap<String, String> header = inputPartFile.getHeaders();
        String fileName = getFileName(header);

        String contentType = inputPartFile.getMediaType().toString();

        log.info("filename : {}", fileName);
        log.info("contentType : {}", contentType);

        java.nio.file.Path path = Paths.get("/tmp/data/" + fileName);

        Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        log.info("requestVo :{}", requestVo);
        inputStream.close();
        return obtainSuccessResponse("SUCCESS FILE UPLOADED : " + requestVo.getRequestMsg());

    }


    private String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }

    @POST
    @Path("/upload-mock-base")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @ApiImplicitParams({
            @ApiImplicitParam(name="data", value = "Request vo Json Añadir el archivo con el .json",
                    dataType = "java.io.File", paramType = "form"),
            @ApiImplicitParam(name="upfile", value = "file",
                    required = true, dataType = "java.io.File", paramType = "form")})
    public Response uploadFileMockBase(MultipartFormDataInput input) throws Exception{

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        RequestVo requestVo = uploadForm.get("data").get(0).getBody(RequestVo.class, RequestVo.class);
        InputPart inputPartFile = uploadForm.get("upfile").get(0);

        log.info("requestVo :{}",requestVo);
        Integer id =saveFileData(inputPartFile);

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


    private Integer saveFileData( InputPart inputPartFile) throws Exception{
        MultivaluedMap<String, String> header = inputPartFile.getHeaders();
        String fileName = getFileName(header);
        String contentType = inputPartFile.getMediaType().toString();
        InputStream inputStream = inputPartFile.getBody(InputStream.class, null);

        Integer id = base.getId();
        log.info("filename : {}",fileName);
        log.info("contentType : {}",contentType);

        FileData fileData = new FileData();
        fileData.setFileName(fileName);
        fileData.setContentType(contentType);
        fileData.setFile(org.apache.commons.io.IOUtils.toByteArray(inputStream));

        log.info("filedata {}",fileData);

        base.put(id,fileData);

        inputStream.close();
        return id;
    }


    @GET
    @Path("/obtain-file-base64")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok everything works")})
    public Response getFileBase64() throws Exception {

        InputStream in = getClass().getClassLoader().getResourceAsStream("image.jpeg");

        ProducerResponseVo producerResponseVo = new ProducerResponseVo();
        producerResponseVo.setMessage("FILA!!");
        producerResponseVo.setImage(obtainImageOnBase64(in));
        return Response.ok().entity(producerResponseVo).build();

    }


    private String obtainImageOnBase64(InputStream in) throws Exception {
        byte[] bytes = org.apache.commons.io.IOUtils.toByteArray(in);
        return Base64.encodeBase64String(bytes);
    }


    @GET
    @Path("/obtain-file")
    @Produces(MediaType.MULTIPART_FORM_DATA)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok everything works, Returns a mulipart/form -> Swagger dont know how to parse this. Use a client like postman")})
    public MultipartFormDataOutput getFileData() {

        InputStream in = getClass().getClassLoader().getResourceAsStream("image.jpeg");

        ProducerResponseVo producerResponseVo = new ProducerResponseVo();
        producerResponseVo.setMessage("FILA!!");

        MultipartFormDataOutput output = new MultipartFormDataOutput();
        output.addFormData("responseVo", producerResponseVo, MediaType.APPLICATION_JSON_TYPE);
        output.addFormData("image", in, MediaType.APPLICATION_OCTET_STREAM_TYPE);
        return output;
    }

    @GET
    @Path("/stream")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getStream() {


        StreamingOutput fileStream = new StreamingOutput() {
            @Override
            public void write(java.io.OutputStream output) throws IOException, WebApplicationException {
                log.info("called write");
                try {
                    InputStream in = getClass().getClassLoader().getResourceAsStream("image.jpeg");
                    byte[] data = org.apache.commons.io.IOUtils.toByteArray(in);
                    output.write(data);
                    output.flush();
                } catch (Exception e) {
                    throw new WebApplicationException("File Not Found !!");
                }
            }
        };
        return Response
                .ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                .header("content-disposition", "attachment; filename = myfile.jpeg")
                .build();


    }


    @GET
    @Path("/obtain-file-map")
    @Produces("multipart/mixed")
    public MultipartOutput getFileDataMap() {

        InputStream in = getClass().getClassLoader().getResourceAsStream("image.jpeg");

        ProducerResponseVo producerResponseVo = new ProducerResponseVo();
        producerResponseVo.setMessage("FILA MAP!!");

        MultipartOutput output = new MultipartOutput();

        output.addPart(producerResponseVo, MediaType.APPLICATION_JSON_TYPE);
        output.addPart(in, MediaType.APPLICATION_OCTET_STREAM_TYPE);
        return output;
    }


    private Response obtainErrorResponse(String msg) {
        ResponseVo responseVo = new ResponseVo();
        responseVo.setFecha(new Date());
        responseVo.setErrorMessage(msg);
        return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(responseVo).build();
    }

    private Response obtainSuccessResponse(String msg, Object... vars) {
        ProducerResponseVo producerResponseVo = new ProducerResponseVo();
        producerResponseVo.setFecha(new Date());
        producerResponseVo.setMessage(MessageFormat.format(msg, vars));
        return Response.ok().entity(producerResponseVo).build();
    }

    private Response obtainSuccessResponse(String msg, String id, Object... vars) {
        ProducerResponseVo producerResponseVo = new ProducerResponseVo();
        producerResponseVo.setFecha(new Date());
        producerResponseVo.setImage(id + "");
        producerResponseVo.setMessage(MessageFormat.format(msg, vars));
        return Response.ok().entity(producerResponseVo).build();
    }


}
