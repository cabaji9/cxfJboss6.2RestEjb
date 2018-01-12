package personal;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.test.TestPortProvider;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Created by Hyun Woo Son on 10/20/17.
 */
public class ProducerMsgTest {

    private static UndertowJaxrsServer server;


    @BeforeClass
    public static void init() throws Exception
    {
        server = new UndertowJaxrsServer().start();
    }

    @AfterClass
    public static void stop() throws Exception
    {
        server.stop();
    }

    @Test
    public void testApplicationPath() throws Exception
    {
        server.deploy(RestApplication.class);
        Client client = ClientBuilder.newClient();
        String val = client.target(TestPortProvider.generateURL("/rest/name"))
                .request().get(String.class);
        Assert.assertEquals("FUNCIONA", val);
        client.close();
    }



}
