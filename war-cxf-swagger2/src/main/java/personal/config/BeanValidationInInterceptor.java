package personal.config;

import org.apache.cxf.jaxrs.lifecycle.ResourceProvider;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationInInterceptor;
import org.apache.cxf.message.Message;

/**
 * This is a replacement for CXF's builtin
 * {@link JAXRSBeanValidationInInterceptor}. This customization supports
 * validation of messages handled by non-singleton JAX-RS resource beans. This
 * is needed as many of the beans in this project are request-scoped.
 */
public class BeanValidationInInterceptor extends
        JAXRSBeanValidationInInterceptor {
    /**
     * This is a customization of the code in CXF's builtin
     * 
     * @see JAXRSBeanValidationInInterceptor#getServiceObject(Message)
     */
    @Override
    protected Object getServiceObject(Message message) {
        final OperationResourceInfo ori = message.getExchange().get(
                OperationResourceInfo.class);
        if (ori == null) {
            return null;
        }
        if (!ori.getClassResourceInfo().isRoot()) {
            return message.getExchange().get(
                    "org.apache.cxf.service.object.last");
        }
        final ResourceProvider resourceProvider = ori.getClassResourceInfo()
                .getResourceProvider();

        return resourceProvider.getInstance(message);
    }
}