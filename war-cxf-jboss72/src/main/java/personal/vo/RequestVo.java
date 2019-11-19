package personal.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import personal.util.TimeConstants;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * Created by Hyun Woo Son on 10/19/17.
 */
@Data
@ApiModel(value="DifferentModel", description="Sample model for the documentation")
public class RequestVo {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]+$")
    @NotNull
    @ApiModelProperty(value = "Valor que va a ser ingresado", allowableValues = "Only letters",required = true,example = "Message")
    private String requestMsg;

    @Min(1)
    @Max(9)
    @NotNull
    @ApiModelProperty(value = "Integer value to be entered", allowableValues = "1..9",required = true,example = "999",dataType = "java.lang.Integer")
    private Integer value;


    @ApiModelProperty(example = "2018-03-22 00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeConstants.DATE_PATTERN, timezone = TimeConstants.TIMEZONE)
    private Date fecha;

    @Size(max = 2)
    private String valorMinimo;

    @ApiModelProperty(example = "A")
    private String estado;


    @JsonIgnore
    @AssertTrue(message = "{validador.tramite.legal.solicitud.estado.a.asg}")
    public boolean isValoresEstadoACorrecto() {
        boolean result = true;
        if ("A".equals(estado)) {
            result = false;
        }
        return result;
    }

}
