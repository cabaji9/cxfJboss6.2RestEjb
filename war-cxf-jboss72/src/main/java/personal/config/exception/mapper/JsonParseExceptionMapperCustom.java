package personal.config.exception.mapper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.jaxrs.base.JsonParseExceptionMapper;
import lombok.extern.slf4j.Slf4j;
import personal.vo.ResponseVo;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Hyun Woo Son on 3/15/18
 **/
@Slf4j
public class JsonParseExceptionMapperCustom extends JsonParseExceptionMapper {


    @Override
    public Response toResponse(JsonParseException exception) {
        log.error("toResponse | Excepcion original : {}",exception.getMessage());
        String currentValueName = "";
        try {
            currentValueName=    exception.getProcessor().getCurrentName();
        }
        catch(Exception e){
            log.error("Ha ocurrido un erro al obtener el nombre del parser.",e.getMessage(),e);
        }

        StringBuilder messageBuild = new StringBuilder();
        messageBuild.append("Ha ocurrido un error al parsear el Json en la propiedad : [");
        messageBuild.append(currentValueName);
        messageBuild.append("] El mensaje error es: ");
        messageBuild.append(reemplazarACastellano(exception.getMessage()));
        messageBuild.append(". Favor corregir el error e intentar otra vez");

        ResponseVo responseVo = new ResponseVo();
        responseVo.setResultado(false);
        responseVo.setMensaje(messageBuild.toString());

        return Response.status(Response.Status.BAD_REQUEST).entity(responseVo).type(MediaType.APPLICATION_JSON).build();

    }


    private String reemplazarACastellano(String message){
        if(message != null){
            message =message.replace("Unexpected character","Caracter no esperado");
            message = message.replace("was expecting comma to separate","se esperaba una coma para separar el ");
        }
        return message;
    }
}
