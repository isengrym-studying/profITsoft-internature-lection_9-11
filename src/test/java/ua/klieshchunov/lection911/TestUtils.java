package ua.klieshchunov.lection911;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

@Component
public class TestUtils {
    @Autowired
    private ObjectMapper objectMapper;

    public <T> T parseResponse(MvcResult mvcResult, Class<T> cls) {
        try {
            return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), cls);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error parsing json", e);
        }
    }
}
