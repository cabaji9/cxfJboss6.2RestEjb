package personal.config.exception.mapper;

import lombok.extern.slf4j.Slf4j;
import personal.vo.ResponseVo;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Iterator;

/**
 * Created by Hyun Woo Son on 1/9/18
 **/
@Slf4j
@Provider
public class ValidationExceptionMapperCustom implements ExceptionMapper<ValidationException> {




        public Response toResponse(ValidationException exception) {
            Response.Status errorStatus = Response.Status.INTERNAL_SERVER_ERROR;
            ResponseVo responseVo = new ResponseVo();
            if (exception instanceof ConstraintViolationException) {
                ConstraintViolationException constraint = (ConstraintViolationException)exception;
                Iterator i$ = constraint.getConstraintViolations().iterator();

                StringBuilder errorMsg = new StringBuilder("Excepcion : ");


                while(i$.hasNext()) {
                    ConstraintViolation<?> violation = (ConstraintViolation)i$.next();
                    log.info("error {}",violation.getRootBeanClass().getSimpleName() + "." + violation.getPropertyPath() + ": " + violation.getMessage());
                    errorMsg.append("La propiedad  -  "+violation.getPropertyPath() + " con error:  " + violation.getMessage() +" ; " );

                }
                responseVo.setMensaje(errorMsg.toString());
            }
            else{
                responseVo.setMensaje("Error inesperado: "+exception.getMessage());
                log.debug("Error de validacion {}",exception.getMessage(),exception);
            }
            return Response.status(errorStatus).entity(responseVo).build();
        }


}
