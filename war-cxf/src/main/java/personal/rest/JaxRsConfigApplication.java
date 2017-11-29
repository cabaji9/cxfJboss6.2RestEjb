package personal.rest;

import io.swagger.jaxrs.config.BeanConfig;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.jaxrs.JsonMappingExceptionMapper;
import org.codehaus.jackson.jaxrs.JsonParseExceptionMapper;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hyun Woo Son on 10/30/17.
 */
public class JaxRsConfigApplication extends Application {

    public JaxRsConfigApplication(){
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.2");
        beanConfig.setSchemes(new String[]{"http","https"});
        beanConfig.setHost("localhost:8080/cxf-web-1.0-SNAPSHOT");
        beanConfig.setBasePath("/api");
        beanConfig.setResourcePackage("personal.rest");
        beanConfig.setScan(true);
        beanConfig.setPrettyPrint(true);
    }


    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(ProducerMsg.class);

        resources.add(JsonMappingExceptionMapper.class);
        resources.add(JsonParseExceptionMapper.class);

        resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        return resources;
    }


    @Override
    public Set<Object> getSingletons() {
        Set<Object> classes = new HashSet<>();
        JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();
        classes.add(jacksonJaxbJsonProvider);
        return classes;
    }
}
