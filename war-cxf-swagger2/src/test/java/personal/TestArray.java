package personal;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Hyun Woo Son on 3/12/18
 **/
public class TestArray {

    private static Logger logger = LoggerFactory.getLogger(TestArray.class);

    @Test
    public void testArray(){



        String[] names = new String[]{"a","b","c"};


        logger.debug("names: {}",appendNames(names));
        logger.debug("names: {}",appendNames(new String[]{"a"}));


    }

    private String appendNames(String[] names){
        int i=0;

        StringBuilder namesString = new StringBuilder();
        for(String name:names){
            namesString.append(name);
            if(i < names.length-1){
                namesString.append(",");
            }
            i++;

        }
        return namesString.toString();
    }
}
