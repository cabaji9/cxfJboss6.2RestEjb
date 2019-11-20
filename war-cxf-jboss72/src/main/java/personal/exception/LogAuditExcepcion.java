package personal.exception;

import java.text.MessageFormat;

/**
 * Created by Hyun Woo Son on 3/13/18
 **/
public class LogAuditExcepcion extends RuntimeException {

    public LogAuditExcepcion(String msg){
        super(msg,null,false,false);
    }


    public LogAuditExcepcion(String msg, String ...vars){
        super(MessageFormat.format(msg,vars),null,false,false);
    }

}
