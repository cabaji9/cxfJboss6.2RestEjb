package personal.rest;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import personal.rest.util.GsonUtil;
import personal.rest.vo.RequestVo;
import personal.rest.vo.ResponseVo;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Hyun Woo Son on 10/22/17.
 */
public class ProducerMsgTest {

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
    public void testAddMsg() {


        RequestVo requestVo = new RequestVo();
        requestVo.setRequestMsg("FUNCIONA!");
        String json = GsonUtil.getInstance().toJson(requestVo);
        List<Object> providers = new ArrayList<>();
        // add custom providers if any
        providers.add(new JacksonJaxbJsonProvider());
        WebClient client = WebClient.create(ENDPOINT_ADDRESS,providers);
        client.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);

        client.path("/rest/sent");
        ResponseVo responseVO = client.post(json,ResponseVo.class);
        assertTrue(responseVO != null);
        assertTrue(responseVO.getMsg().equals("WORKS"));
    }


}
