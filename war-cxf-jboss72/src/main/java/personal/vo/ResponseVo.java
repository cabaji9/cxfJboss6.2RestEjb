package personal.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import personal.util.TimeConstants;

import java.util.Date;

/**
 * Created by Hyun Woo Son on 10/19/17.
 */
@Data
public class ResponseVo {

    private String errorMessage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeConstants.DATE_PATTERN, timezone = TimeConstants.TIMEZONE)
    private Date fecha;

}
