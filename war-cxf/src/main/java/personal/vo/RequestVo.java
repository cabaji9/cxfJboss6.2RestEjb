package personal.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.*;

/**
 * Created by Hyun Woo Son on 10/19/17.
 */
@ApiModel(value="DifferentModel", description="Sample model for the documentation")
public class RequestVo {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]+$")
    @NotNull
    private String requestMsg;

    @Min(1)
    @Max(9)
    @NotNull
    private Integer value;

    @Size(max = 2)
    private String valorMinimo;

    public String getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(String valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    @ApiModelProperty(value = "Valor que va a ser ingresado", allowableValues = "Only letters",required = true,example = "Message")
    public String getRequestMsg() {
        return requestMsg;
    }

    public void setRequestMsg(String requestMsg) {
        this.requestMsg = requestMsg;
    }

    @ApiModelProperty(value = "Integer value to be entered", allowableValues = "1..9",required = true,example = "999",dataType = "java.lang.Integer")
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "RequestVo{" +
                "requestMsg='" + requestMsg + '\'' +
                ", value=" + value +
                '}';
    }
}
