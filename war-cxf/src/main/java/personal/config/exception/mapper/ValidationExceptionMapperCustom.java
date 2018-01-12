package personal.config.exception.mapper;


import org.apache.cxf.validation.ResponseConstraintViolationException;
import org.slf4j.LoggerFactory;
import personal.enumeration.RestExcepcionEnum;
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
@Provider
public class ValidationExceptionMapperCustom implements ExceptionMapper<ValidationException> {


    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ValidationExceptionMapperCustom.class);


        public Response toResponse(ValidationException exception) {
            Response.Status errorStatus = Response.Status.INTERNAL_SERVER_ERROR;
            ResponseVo responseVo = new ResponseVo();
            if (exception instanceof ConstraintViolationException) {
                ConstraintViolationException constraint = (ConstraintViolationException)exception;
                Iterator i$ = constraint.getConstraintViolations().iterator();

                StringBuilder errorMsg = new StringBuilder(RestExcepcionEnum.PARAMETROS_EXCEPCION.getValue());


                while(i$.hasNext()) {
                    ConstraintViolation<?> violation = (ConstraintViolation)i$.next();
                    logger.info("error {}",violation.getRootBeanClass().getSimpleName() + "." + violation.getPropertyPath() + ": " + violation.getMessage());
                    errorMsg.append("La propiedad  -  "+violation.getPropertyPath() + " con error:  " + violation.getMessage() +" ; " );

                }

                if (!(constraint instanceof ResponseConstraintViolationException)) {
                    errorStatus = Response.Status.BAD_REQUEST;
                }
                responseVo.setErrorMessage(errorMsg.toString());
            }
            else{
                responseVo.setErrorMessage("Error inesperado: "+exception.getMessage());
                logger.debug("Error de validacion {}",exception.getMessage(),exception);
            }
            return Response.status(errorStatus).entity(responseVo).build();
        }


}
