package personal;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.BeforeClass;
import org.junit.Test;
import personal.rest.ProducerMsg;
import personal.util.GsonUtil;
import personal.vo.ProducerResponseVo;
import personal.vo.RequestVo;
import personal.vo.ResponseVo;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Slf4j
public class TestProducerMsg {

    private static Dispatcher dispatcher;

    @BeforeClass
    public static void init() {
        dispatcher = TestUtil.startServer(new ProducerMsg());
    }

    @Test
    public void testRestSent() throws Exception {
        RequestVo requestVo = new RequestVo();
        requestVo.setEstado("C");
        requestVo.setFecha(new Date());
        requestVo.setValorMinimo("1");
        requestVo.setValue(1);
        requestVo.setRequestMsg("lkalkaj");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(requestVo);
        MockHttpRequest mockHttpRequest = TestUtil.getMockRequest(HttpMethod.POST, "/rest/sent", json);
        MockHttpResponse response = TestUtil.sendHttpRequest(mockHttpRequest, dispatcher);
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());

        ProducerResponseVo producerResponseVo = obtainEntity(response, ProducerResponseVo.class);

        log.info("producer {}",producerResponseVo);


    }


    @Test
    public void testRestSentError() throws Exception {
        RequestVo requestVo = new RequestVo();
        requestVo.setEstado("A");
        requestVo.setFecha(new Date());
        requestVo.setValorMinimo("1");
        requestVo.setValue(4);
        requestVo.setRequestMsg("lkalkaj");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(requestVo);
        MockHttpRequest mockHttpRequest = TestUtil.getMockRequest(HttpMethod.POST, "/rest/sent", json);
        MockHttpResponse response = TestUtil.sendHttpRequest(mockHttpRequest, dispatcher);
        assertEquals(response.getStatus(),Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

        ResponseVo producerResponseVo = obtainEntity(response, ResponseVo.class);

        log.info("ResponseVo {}",producerResponseVo);


    }


    public <T> T obtainEntity(MockHttpResponse response, Class<T> valueType) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.getContentAsString(),valueType);
    }

    public <T> T obtainEntity(Response response, TypeReference valueTypeRef) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue((InputStream) response.getEntity(), valueTypeRef);
    }
}
