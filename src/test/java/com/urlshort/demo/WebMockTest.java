package com.urlshort.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest
@AutoConfigureMockMvc
public class WebMockTest {
    private static final String URL_TEST = "http://java.com";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldRedirect() throws Exception {
        final String URL_TEST = "http://java.com";

        ObjectMapper mapper = new ObjectMapper();

        MvcResult mvcResult = this.mockMvc.perform(post("/data/shorten").contentType(MediaType.APPLICATION_JSON)
                .param("fullUrl", URL_TEST))
                .andDo(print()).andExpect(status().isAccepted()).andReturn();
        ;
        Map map = mapper.readValue(mvcResult.getResponse().getContentAsString(), Map.class);

        Assert.assertTrue(map.containsKey("id"));

        String key = map.get("id").toString();

        MvcResult getResult = this.mockMvc.perform(get(String.format("/%s", key)))
                .andDo(print()).andExpect(status().isSeeOther()).andReturn();

        MockHttpServletResponse response = getResult.getResponse();

        Assert.assertTrue(response.containsHeader("Location"));

        String location = response.getHeader("Location");

        Assert.assertEquals(URL_TEST, location);
    }

    @Test
    public void shouldReturnFullUrl() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        MvcResult mvcResult = this.mockMvc.perform(post("/data/shorten").contentType(MediaType.APPLICATION_JSON)
                .param("fullUrl", URL_TEST))
                .andDo(print()).andExpect(status().isAccepted()).andReturn();
        ;
        Map map = mapper.readValue(mvcResult.getResponse().getContentAsString(), Map.class);

        Assert.assertTrue(map.containsKey("id"));

        String key = map.get("id").toString();

        MvcResult getResult = this.mockMvc.perform(get(String.format("/data/expand/%s", key)))
                .andDo(print()).andExpect(status().isAccepted()).andReturn();

        Map mapResponce = mapper.readValue(mvcResult.getResponse().getContentAsString(), Map.class);

        Assert.assertTrue(mapResponce.containsKey("fullUrl"));

        String location = mapResponce.get("fullUrl").toString();

        Assert.assertEquals(URL_TEST, location);
    }
}
