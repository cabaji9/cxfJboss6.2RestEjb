package personal.ejb;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Hyun Woo Son on 10/10/17.
 */
@Stateless
public class TestEJBImpl implements TestEJBLocal,TestEJBRemote {

   private static Logger log = Logger.getLogger(TestEJBImpl.class.getName());


    List<String> nameList = new ArrayList<String>();

    @Override
    public void addName(String addName) {
        log.info("ADDED NAME:"+ addName);
        nameList.add(addName);
    }


    @Override
    public String obtainName() {
        return "FUNCIONA!!";
    }
}
