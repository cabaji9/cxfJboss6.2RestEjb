package personal.rest;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hyun Woo Son on 10/20/17.
 */
public class RestApplication extends Application {

    private Set<Object> singletons = new HashSet<Object>();

    public RestApplication() {
        singletons.add(new ProducerMsg());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
