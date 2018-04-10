package personal.config.exception.mapper;

import org.apache.cxf.jaxrs.impl.WebApplicationExceptionMapper;
import org.apache.cxf.jaxrs.utils.multipart.MultipartReadException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Hyun Woo Son on 3/24/18
 **/
public class WebApplicationExceptionMapperCustom extends WebApplicationExceptionMapper {

    @Override
    public Response toResponse(WebApplicationException ex) {
        if(ex.getCause() != null && ex.getCause() instanceof MultipartReadException){
            String errorMessage ="Ha ocurrido un error al llamar al servicio. Verifique los parámetros. El error es : "+ex.getCause().getMessage();
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).type(MediaType.TEXT_PLAIN).build();
        }
        else{
            return super.toResponse(ex);
        }



    }
}
