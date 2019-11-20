package personal;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.core.SynchronousDispatcher;
import org.jboss.resteasy.core.SynchronousExecutionContext;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.spi.validation.GeneralValidator;
import personal.config.exception.mapper.JsonMappingExceptionMapperCustom;
import personal.config.exception.mapper.JsonParseExceptionMapperCustom;
import personal.config.exception.mapper.LogAuditExceptionMapperCustom;
import personal.config.exception.mapper.ValidationExceptionMapperCustom;
import personal.interceptor.LoggingInterceptor;

import javax.ws.rs.core.MediaType;
import java.net.URISyntaxException;

public class TestUtil {


    public static Dispatcher startServer(Object resource){
        Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
        dispatcher.getRegistry().addSingletonResource(resource);

        dispatcher.getProviderFactory().registerProvider(GeneralValidator.class);
        dispatcher.getProviderFactory().registerProvider(JacksonJaxbJsonProvider.class);
        dispatcher.getProviderFactory().registerProvider(LogAuditExceptionMapperCustom.class);
        dispatcher.getProviderFactory().registerProvider(JsonParseExceptionMapperCustom.class);
        dispatcher.getProviderFactory().registerProvider(JsonMappingExceptionMapperCustom.class);
        dispatcher.getProviderFactory().registerProvider(ValidationExceptionMapperCustom.class);
        dispatcher.getProviderFactory().registerProvider(LoggingInterceptor.class);
        return dispatcher;
    }


    public static MockHttpRequest getMockRequest(String method, String uri,String content) throws URISyntaxException {
        MockHttpRequest request =MockHttpRequest.create(method,uri);
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON_TYPE);
        request.content(content.getBytes());
        return request;
    }


    public static MockHttpResponse sendHttpRequest(MockHttpRequest request,Dispatcher dispatcher)  {
        MockHttpResponse response = new MockHttpResponse();
        SynchronousExecutionContext synchronousExecutionContext = new SynchronousExecutionContext((SynchronousDispatcher) dispatcher, request, response);
        request.setAsynchronousContext(synchronousExecutionContext);
        dispatcher.invoke(request, response);
        return response;
    }

}
