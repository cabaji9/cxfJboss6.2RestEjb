package personal.rest;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by Hyun Woo Son on 10/11/17.
 */
public class Lookup {

    public static Object getEjb(String lookupName){
        Object t = null;
        try {
            Context ctx = new InitialContext();
             t=  ctx.lookup(lookupName);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return t;
    }
}
