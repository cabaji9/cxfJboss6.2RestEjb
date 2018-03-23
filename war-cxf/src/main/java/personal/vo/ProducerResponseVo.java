package personal.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Hyun Woo Son on 1/12/18
 **/
@ApiModel(value="ProducerResponseVo", description="Descripcion de la respuesta")
public class ProducerResponseVo {

    @ApiModelProperty(value = "Valor devuelto por el servicio", allowableValues = "anything",required = true,example = "Success")

    private String message;

    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
