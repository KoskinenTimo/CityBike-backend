package org.citybike.stations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class StationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void statusOk() throws Exception {
        mockMvc.perform(get("/api/stations"))
                .andExpect(status().isOk());
    }


    @Test
    public void getStations() throws Exception {
        MvcResult res = mockMvc.perform(get("/api/stations"))
                .andReturn();
        String content = res.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("content"));
    }

    @Test
    public void postAndDeleteStation() throws Exception {
        MvcResult res = mockMvc.perform(get("/api/stations/999"))
                .andReturn();
        String content = res.getResponse().getContentAsString();
        Assertions.assertFalse(content.contains("999"));

        Station station = new Station();
        station.setIdentifier(new Long(999));
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(station);
        mockMvc.perform(post("/api/stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isOk());

        res = mockMvc.perform(get("/api/stations/999"))
                .andReturn();
        content = res.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("identifier"));

        mockMvc.perform(delete("/api/stations/999"))
                .andExpect(status().isOk());
    }
}