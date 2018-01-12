package personal.config;

import com.fasterxml.jackson.jaxrs.base.JsonMappingExceptionMapper;
import com.fasterxml.jackson.jaxrs.base.JsonParseExceptionMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import io.swagger.jaxrs.config.BeanConfig;
import personal.config.exception.mapper.ValidationExceptionMapperCustom;
import personal.rest.ProducerMsg;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hyun Woo Son on 10/30/17.
 */
public class JaxRsConfigApplication extends Application {

    public JaxRsConfigApplication(){
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http","https"});
        beanConfig.setBasePath("/cxf-example/api");
        beanConfig.setResourcePackage("personal.rest");
        beanConfig.setDescription("Servicios Rest ");
        beanConfig.setTitle("Servicios Rest Ejemplo");

        beanConfig.setContact("cabaji9@gmail.com");
        beanConfig.setLicense("Open source");
        beanConfig.setScan(true);
        beanConfig.setPrettyPrint(true);
    }


    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(ProducerMsg.class);

        resources.add(JsonMappingExceptionMapper.class);
        resources.add(JsonParseExceptionMapper.class);

        //bean validation
        resources.add(ValidationExceptionMapperCustom.class);
        resources.add(BeanValidationInInterceptor.class);

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
