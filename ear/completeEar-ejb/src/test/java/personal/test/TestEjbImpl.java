package personal.test;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import personal.ejb.TestEJBRemote;
import personal.model.Persona;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

/**
 * Created by Hyun Woo Son on 2/7/18
 **/
@Ignore
public class TestEjbImpl {

    @Rule
    public TestWatcher name = new TestWatcherMethodName();

    private static Logger logger = LoggerFactory.getLogger(TestEjbImpl.class);

    @Test
    public void testEjb() throws Exception{



        Properties prop = new Properties();

        prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        prop.put(Context.PROVIDER_URL, "remote://localhost:4447");
        prop.put(Context.SECURITY_PRINCIPAL, "admin");
        prop.put(Context.SECURITY_CREDENTIALS, "admin$1234");
        prop.put("jboss.naming.client.ejb.context", true);

        Context context = new InitialContext(prop);



        TestEJBRemote ejbRemote =  (TestEJBRemote) context.lookup("completeEar/completeEar-web-1.0-SNAPSHOT/TestEJBImpl!personal.ejb.TestEJBRemote");

        ejbRemote.deleteAll();


        ejbRemote.addName("uno");
        ejbRemote.addName("dos");


        List<Persona> personaList =
                ejbRemote.obtainAllPersonas();

        logger.info("lista de personas {}",personaList);

        assertTrue(personaList.size() == 2);

        ejbRemote.deletePersonaByName("uno");

         personaList =
                ejbRemote.obtainAllPersonas();

        logger.info("lista de personas {}",personaList);

        assertTrue(personaList.size() == 1);


        ejbRemote.deletePersonaByName("dos");



        context.close();

    }



}
