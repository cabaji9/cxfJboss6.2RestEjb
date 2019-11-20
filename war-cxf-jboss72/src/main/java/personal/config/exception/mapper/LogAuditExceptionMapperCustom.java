package personal.config.exception.mapper;

import lombok.extern.slf4j.Slf4j;
import personal.exception.LogAuditExcepcion;
import personal.vo.ResponseVo;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by Hyun Woo Son on 1/9/18
 **/
@Slf4j
@Provider
public class LogAuditExceptionMapperCustom implements ExceptionMapper<LogAuditExcepcion> {




        public Response toResponse(LogAuditExcepcion exception) {
            Response.Status errorStatus = Response.Status.BAD_REQUEST;
            ResponseVo responseVo = new ResponseVo();
            if (exception instanceof LogAuditExcepcion) {
                responseVo.setMensaje(exception.getMessage());
                log.error("Ingreso al mapeador de excepciones",exception.getMessage());
            }
            else{
                responseVo.setMensaje("Error inesperado: "+exception.getMessage());
            }
            return Response.status(errorStatus).entity(responseVo).type(MediaType.APPLICATION_JSON).build();
        }


}
