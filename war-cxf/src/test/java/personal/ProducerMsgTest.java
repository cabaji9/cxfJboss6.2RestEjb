package personal;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationInInterceptor;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationOutInterceptor;
import org.apache.cxf.message.Message;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import personal.config.BeanValidationInInterceptor;
import personal.config.exception.mapper.ValidationExceptionMapperCustom;
import personal.rest.ProducerMsg;
import personal.util.GsonUtil;
import personal.util.RestUtil;
import personal.vo.ProducerResponseVo;
import personal.vo.RequestVo;
import personal.vo.ResponseVo;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Hyun Woo Son on 10/22/17.
 */
public class ProducerMsgTest {

    private static Logger logger = LoggerFactory.getLogger(ProducerMsgTest.class);

    private static Server server;
    private final static String ENDPOINT_ADDRESS = "http://localhost:9000";

    @BeforeClass
    public static void initialize() throws Exception {
        startServer();
    }


    private static void startServer() throws Exception {
        JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
        sf.setResourceClasses(ProducerMsg.class);

        List<Object> providers = new ArrayList<>();
        // add custom providers if any
        providers.add(new JacksonJaxbJsonProvider());

        sf.setProvider(new ValidationExceptionMapperCustom());
        sf.setInInterceptors(Arrays.<Interceptor< ? extends Message>>asList(new BeanValidationInInterceptor()));
        sf.setProviders(providers);
        sf.setResourceProvider(ProducerMsg.class,
                new SingletonResourceProvider(new ProducerMsg(), true));
        sf.setAddress(ENDPOINT_ADDRESS);
        server = sf.create();
    }

    @AfterClass
    public static void destroy() throws Exception {
        server.stop();
        server.destroy();
    }

    @Test
    public void testAddMsg() throws Exception {


        RequestVo requestVo = new RequestVo();
        requestVo.setRequestMsg("FUNCIONA");
        requestVo.setValue(1);
        String json = GsonUtil.getInstance().toJson(requestVo);
        WebClient client = obtainClient(ENDPOINT_ADDRESS);
        Response response = client.post(json,Response.class);
        assertTrue(response != null);

        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
        ProducerResponseVo responseVo =RestUtil.obtainEntity(response,ProducerResponseVo.class);
        assertTrue(responseVo.getMessage().contains("FUNCIONA"));

    }



    @Test
    public void testAddMsgError() throws Exception {


        RequestVo requestVo = new RequestVo();
        requestVo.setRequestMsg("FUNCIONA!!");
        requestVo.setValue(987389);
        String json = GsonUtil.getInstance().toJson(requestVo);
        WebClient client = obtainClient(ENDPOINT_ADDRESS);
        Response response = client.post(json,Response.class);
        assertTrue(response != null);

        assertTrue(response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
        ResponseVo responseVo =RestUtil.obtainEntity(response,ResponseVo.class);

        logger.info("response {}",responseVo);
    }


    private WebClient obtainClient(String ENDPOINT_ADDRESS){
        List<Object> providers = new ArrayList<>();
        // add custom providers if any
        providers.add(new JacksonJaxbJsonProvider());
        WebClient client = WebClient.create(ENDPOINT_ADDRESS,providers);
        client.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
        client.path("/rest/sent");
        return  client;
    }


}
