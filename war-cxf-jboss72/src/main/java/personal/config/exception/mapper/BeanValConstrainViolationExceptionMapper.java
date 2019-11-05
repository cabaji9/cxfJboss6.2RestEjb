package personal.config.exception.mapper;

import lombok.extern.slf4j.Slf4j;
import personal.dto.ConstraintViolationEntityDto;
import personal.dto.ConstraintViolationFieldDto;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Provider
public class BeanValConstrainViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException e) {
       log.info("toResponse | BeanValConstrainViolationExceptionMapper in action exceptinos");
       List<ConstraintViolationFieldDto> constraintViolationFieldDtoList =  e.getConstraintViolations().stream().map(ex ->
           new ConstraintViolationFieldDto(ex.getPropertyPath().toString(),ex.getMessage())
           ).collect(Collectors.toList());
        return Response.status(Response.Status.NOT_ACCEPTABLE)
                .entity( new ConstraintViolationEntityDto("Ocurrio un error de validacion",constraintViolationFieldDtoList))
                .build();
    }
}