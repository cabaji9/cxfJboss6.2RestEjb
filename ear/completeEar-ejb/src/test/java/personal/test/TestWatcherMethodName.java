package personal.test;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Hyun Woo Son on 1/16/18
 **/
public class TestWatcherMethodName extends TestWatcher {

    private static Logger logger = LoggerFactory.getLogger(TestWatcherMethodName.class);

    protected void starting(Description description) {
        logger.info("\n\n********Iniciando prueba:  {} ************\n ", description.getMethodName());
    }


}
