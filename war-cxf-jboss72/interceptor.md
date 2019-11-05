
## INTERCEPTORS CXF

1. Create an interceptor class that inherits from AbstractPhaseInterceptor<Message> and implements ContainerRequestFilter
```java
public class LogInPreInvokeInterceptor extends AbstractPhaseInterceptor<Message> implements ContainerRequestFilter {
```
2. On the constructor add call to super withe the Phase wanted to intercept.
```java
public LogInPreInvokeInterceptor() {
    super(Phase.PRE_INVOKE);
}

public LogInPreInvokeInterceptor(String phase) {
    super(phase);
}
```
2.1. Additionally it can be added where the interceptor on the phase should be added or after who

```java
addAfter(BeanValidationInInterceptor.class.getName());
```

This goes on the constructor.


3. There are several phases to choose:

- RECEIVE
Transport level processing

- (PRE/USER/POST)_STREAM
Stream level processing/transformations

- READ
This is where header reading typically occurs.

- (PRE/USER/POST)_PROTOCOL
Protocol processing, such as JAX-WS SOAP handlers

- UNMARSHAL
Unmarshalling of the request

- (PRE/USER/POST)_LOGICAL
Processing of the umarshalled request
- PRE_INVOKE
Pre invocation actions
- INVOKE
Invocation of the service
- POST_INVOKE
Invocation of the outgoing chain if there is one


4. Override two methods, handle Message  -> and filter on the filter

The filter has the following logic to add the interceptor to the chain of interceptors

```java
public void filter(ContainerRequestContext context) {
    InterceptorChain chain = PhaseInterceptorChain.getCurrentMessage().getInterceptorChain();
    chain.add(this);
}
```
5. The handleMessage method should be used to do with the message the operations needed.
