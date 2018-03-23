package personal.util;


import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Hyun Woo Son on 12/14/17
 **/
public class RestUtil {


    public static <T> T obtainEntity(Response response, Class<T> valueType) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue((InputStream) response.getEntity(), valueType);
    }
}
