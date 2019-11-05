package personal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Hyun Woo Son on 11/5/19
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConstraintViolationFieldDto {

    private String field;
    private String error;


}
