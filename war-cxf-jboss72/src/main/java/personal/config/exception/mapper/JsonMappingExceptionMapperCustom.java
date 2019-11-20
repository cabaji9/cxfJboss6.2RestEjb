package personal.config.exception.mapper;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.jaxrs.base.JsonMappingExceptionMapper;
import lombok.extern.slf4j.Slf4j;
import personal.vo.ResponseVo;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Hyun Woo Son on 3/15/18
 **/
@Slf4j
public class JsonMappingExceptionMapperCustom extends JsonMappingExceptionMapper {


    @Override
    public Response toResponse(JsonMappingException exception) {
        log.error("toResponse | Excepcion original : {}",exception.getMessage());
        String message = exception.getMessage();


        if(exception instanceof InvalidFormatException){
            InvalidFormatException invalidFormatException =(InvalidFormatException)exception;
            String value = invalidFormatException.getValue() != null ?invalidFormatException.getValue().toString():null;
            String type = invalidFormatException.getTargetType()!= null ?invalidFormatException.getTargetType().getName():null;
            String nombreCampo = obtenerNombreCampo(invalidFormatException);

            StringBuilder messageBuild = new StringBuilder();
            messageBuild.append("El valor ingresado : '");
            messageBuild.append(value);
            messageBuild.append("' no corresponde al tipo: ");
            messageBuild.append(type);
            messageBuild.append(", en el campo: [");
            messageBuild.append(nombreCampo);
            messageBuild.append("] . Favor corregir y volver a intentar");
            message = messageBuild.toString();
        }
        ResponseVo responseVo = new ResponseVo();
        responseVo.setResultado(false);
        responseVo.setMensaje(message);

        return Response.status(Response.Status.BAD_REQUEST).entity(responseVo).type(MediaType.APPLICATION_JSON).build();
    }


    private String obtenerNombreCampo(InvalidFormatException invalidFormatException){
        String nombreCampo = "";
        List<JsonMappingException.Reference> referenceList = invalidFormatException.getPath();
        if(referenceList != null && !referenceList.isEmpty()){
          JsonMappingException.Reference reference = referenceList.get(0);
          if(reference != null){
                nombreCampo =reference.getFieldName();
          }
        }
        return nombreCampo;
    }
}
