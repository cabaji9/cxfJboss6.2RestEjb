package personal.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.core.ResourceMethodInvoker;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

@Slf4j
@Provider
public class LoggingInterceptor implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        log.info("{}",requestContext);
        String servicePath =requestContext.getUriInfo().getPath();
        String module = requestContext.getUriInfo().getBaseUri().getPath();
        obtainParameterNames(obtainMethod(requestContext));

        if(MediaType.APPLICATION_JSON_TYPE.isCompatible(requestContext.getMediaType()))  {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(requestContext.getEntityStream(), baos);
            byte[] bytes = baos.toByteArray();
            log.info("Posted: service {} module {} object  {}" ,servicePath,module,new String(bytes, "UTF-8"));
            requestContext.setEntityStream(new ByteArrayInputStream(bytes));
        }


    }


    private Method obtainMethod(ContainerRequestContext context){
        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) context.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        return methodInvoker.getMethod();
    }


    protected String obtainParameterNames(Method method) {
        StringBuilder parameterNames = new StringBuilder("Parameters are: ");
        Class[] parameters = method.getParameterTypes();
        int i = 0;
        for (Class parameterClass : parameters) {
            String name = parameterClass.getName();
            log.debug("parameter : {}", name);
            parameterNames.append(name);
            if (i < parameters.length - 1) {
                parameterNames.append(" , ");
            }
            i++;
        }
        return parameterNames.toString();
    }
}
