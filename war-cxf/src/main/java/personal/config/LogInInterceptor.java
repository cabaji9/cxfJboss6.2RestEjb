package personal.config;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.service.invoker.MethodDispatcher;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Hyun Woo Son on 3/12/18
 **/
public class LogInInterceptor extends AbstractPhaseInterceptor<Message> implements ContainerRequestFilter {

    private static Logger logger = LoggerFactory.getLogger(LogInInterceptor.class);

    public LogInInterceptor(){
        super(Phase.PRE_INVOKE);
    }

    @Override
    public void handleMessage(Message message) throws Fault {

        Object object = getInvokee(message);
        logger.debug("Called method :{}", getServiceMethod(message).getName());
        logger.debug("Called object :{}", object);

        logger.debug("url:{}",message.get(Message.REQUEST_URL));

        logger.debug("Paramters: {}",obtainParameterNames(getServiceMethod(message)));

        logger.debug(object.toString());

    }


    private String obtainParameterNames(Method method){
        StringBuilder parameterNames = new StringBuilder("Parameters are: ");
       Class[] parameters = method.getParameterTypes();
       int i=0;
       for(Class parameterClass:parameters){
            String name = parameterClass.getName();
            logger.debug("parameter : {}",name);
            parameterNames.append(name);
            if(i < parameters.length-1){
                parameterNames.append(" , ");
            }
            i++;
        }
        return  parameterNames.toString();
    }

    private Object getInvokee(Message message) {
        Object invokee = message.getContent(List.class);
        if (invokee == null) {
            invokee = message.getContent(Object.class);
        }

        return invokee;
    }


    protected Method getServiceMethod(Message message) {
        Message inMessage = message.getExchange().getInMessage();
        Method method = (Method) inMessage.get("org.apache.cxf.resource.method");
        if (method == null) {
            BindingOperationInfo bop = inMessage.getExchange().getBindingOperationInfo();
            if (bop != null) {
                MethodDispatcher md = (MethodDispatcher) inMessage.getExchange().getService().get(MethodDispatcher.class.getName());
                method = md.getMethod(bop);
            }
        }

        return method;
    }


    public void filter(ContainerRequestContext context) {
        InterceptorChain chain = PhaseInterceptorChain.getCurrentMessage().getInterceptorChain();
        chain.add(this);
    }
}
