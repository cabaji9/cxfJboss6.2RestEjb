package personal.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Provider
public class LoggingInterceptor implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        log.info("{}",requestContext);
        String servicePath =requestContext.getUriInfo().getPath();
        String module = requestContext.getUriInfo().getBaseUri().getPath();

        if(MediaType.APPLICATION_JSON_TYPE.isCompatible(requestContext.getMediaType()))  {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(requestContext.getEntityStream(), baos);
            byte[] bytes = baos.toByteArray();
            log.info("Posted: service {} module {} object  {}" ,servicePath,module,new String(bytes, "UTF-8"));
            requestContext.setEntityStream(new ByteArrayInputStream(bytes));
        }


    }
}
