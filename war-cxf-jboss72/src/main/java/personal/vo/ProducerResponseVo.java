package personal.vo;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import personal.util.TimeConstants;

import java.util.Date;

/**
 * Created by Hyun Woo Son on 1/12/18
 **/
@ApiModel(value="ProducerResponseVo", description="Descripcion de la respuesta")
@Data
public class ProducerResponseVo {

    @ApiModelProperty(value = "Valor devuelto por el servicio", allowableValues = "anything", required = true, example = "Success")

    private String message;

    private String image;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeConstants.DATE_PATTERN, timezone = TimeConstants.TIMEZONE)
    private Date fecha;

}
