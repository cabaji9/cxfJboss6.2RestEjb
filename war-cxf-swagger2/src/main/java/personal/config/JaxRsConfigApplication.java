package personal.config;

import com.fasterxml.jackson.jaxrs.base.JsonMappingExceptionMapper;
import com.fasterxml.jackson.jaxrs.base.JsonParseExceptionMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.sun.el.stream.Stream;
import io.swagger.oas.integration.SwaggerConfiguration;
import io.swagger.oas.models.OpenAPI;
import io.swagger.oas.models.info.Contact;
import io.swagger.oas.models.info.Info;
import io.swagger.oas.models.info.License;
import org.apache.cxf.interceptor.LoggingInInterceptor;
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


        OpenAPI oas = new OpenAPI();
        Info info = new Info()
                .title("Swagger Sample App")
                .description("This is a sample server Petstore server.  You can find out more about Swagger " +
                        "at [http://swagger.io](http://swagger.io) or on [irc.freenode.net, #swagger](http://swagger.io/irc/).  For this sample, " +
                        "you can use the api key `special-key` to test the authorization filters.")
                .termsOfService("http://swagger.io/terms/")
                .contact(new Contact()
                        .email("apiteam@swagger.io"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0.html"));

        oas.info(info);

        Set<String> set = new HashSet<>();
        set.add("personal");

        SwaggerConfiguration oasConfig = new SwaggerConfiguration()
                .openAPI(oas)
                .resourcePackages(set);


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
        resources.add(LoggingInInterceptor.class);
        resources.add(LogInInterceptor.class);

        resources.add(io.swagger.jaxrs2.integration.resources.OpenApiResource.class);


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
