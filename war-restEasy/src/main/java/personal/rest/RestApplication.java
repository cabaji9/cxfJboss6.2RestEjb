package personal.rest;

import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hyun Woo Son on 10/20/17.
 */
public class RestApplication extends Application {



    private Set<Object> singletons = new HashSet<Object>();

    public RestApplication(){
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.2");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/");
        beanConfig.setResourcePackage("personal.rest");
        beanConfig.setScan(true);
        beanConfig.setPrettyPrint(true);
    }



    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(ProducerMsg.class);

        resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        return resources;
    }



    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
