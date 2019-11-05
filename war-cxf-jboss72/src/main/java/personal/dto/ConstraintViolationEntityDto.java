package personal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Hyun Woo Son on 11/5/19
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConstraintViolationEntityDto {

    private String msg;
    private List<ConstraintViolationFieldDto> fieldList;




}
