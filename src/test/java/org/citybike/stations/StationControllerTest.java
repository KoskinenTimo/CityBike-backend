package org.citybike.stations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.citybike.entity.Location;
import org.citybike.entity.Station;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StationControllerTest {
    @LocalServerPort
    private Integer port;
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
        Long id = new Long(999);
        MvcResult res = mockMvc.perform(get("/api/stations/999"))
                .andReturn();
        String content = res.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("Station not found with id " + id));

        Station station = createValidStation(id);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(station);
        mockMvc.perform(post("/api/stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isCreated());

        res = mockMvc.perform(get("/api/stations/" + id))
                .andReturn();
        content = res.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("identifier"));

        mockMvc.perform(delete("/api/stations/" + id))
                .andExpect(status().isOk());
        res = mockMvc.perform(get("/api/stations/999"))
                .andReturn();
        content = res.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("Station not found with id " + id));
    }
    public Station createValidStation (Long id) {
        Location location = Location.build(
                "+24.977548",
                "+60.189556");

        return Station.build(
                id,
                "Test nimi",
                "Test namn",
                "Test name",
                "Test osoite",
                "Test adress",
                "Test kaupunki",
                "Test stad",
                "Test operaattori",
                10,
                location);
    }
}