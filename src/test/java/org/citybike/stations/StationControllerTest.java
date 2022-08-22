package org.citybike.stations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.citybike.entity.Location;
import org.citybike.entity.Station;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
    public void getStationsReturnsPage() throws Exception {
        MvcResult res = mockMvc.perform(get("/api/stations"))
                .andReturn();
        String content = res.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("content"));
    }

    @Test
    public void postAndDeleteStationSuccesfully() throws Exception {
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

    @Test
    public void postStationWithBadDataReturnsCorrectErrorMessages() throws Exception {
        Long id = new Long(999);
        Station station = new Station();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(station);
        MvcResult res = mockMvc.perform(post("/api/stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andReturn();

        String content = res.getResponse().getContentAsString();
        JSONParser parser = new JSONParser();
        JSONObject jsonRes = (JSONObject) parser.parse(content);
        Assertions.assertTrue(jsonRes.containsKey("identifier"));
        Assertions.assertTrue(jsonRes.containsKey("nimi"));
        Assertions.assertTrue(jsonRes.containsKey("kapasiteetit"));
        Assertions.assertTrue(jsonRes.containsKey("location"));
        Assertions.assertTrue(jsonRes.containsKey("kaupunki"));
        Assertions.assertTrue(jsonRes.containsKey("osoite"));
        Assertions.assertTrue(jsonRes.containsValue("Identifier is required"));
        Assertions.assertTrue(jsonRes.containsValue("Nimi is required"));
        Assertions.assertTrue(jsonRes.containsValue("Kapasiteetit must be greater than zero"));
        Assertions.assertTrue(jsonRes.containsValue("Location is required"));
        Assertions.assertTrue(jsonRes.containsValue("Kaupunki is required"));
        Assertions.assertTrue(jsonRes.containsValue("Osoite is required"));

        station = createValidStation(new Long(id));
        json = ow.writeValueAsString(station);
        mockMvc.perform(post("/api/stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isCreated());
        res = mockMvc.perform(post("/api/stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andReturn();

        content = res.getResponse().getContentAsString();
        jsonRes = (JSONObject) parser.parse(content);
        Assertions.assertTrue(jsonRes.containsKey("identifier"));
        Assertions.assertTrue(jsonRes.containsValue("Identifier must be unique, given identifier already exists"));
        mockMvc.perform(delete("/api/stations/" + id))
                .andExpect(status().isOk());

        Location location = Location.build(".977548",".189556");
        station.setLocation(location);
        json = ow.writeValueAsString(station);
        res = mockMvc.perform(post("/api/stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andReturn();

        content = res.getResponse().getContentAsString();
        jsonRes = (JSONObject) parser.parse(content);
        Assertions.assertTrue(jsonRes.containsKey("location.latitude"));
        Assertions.assertTrue(jsonRes.containsKey("location.longitude"));
        Assertions.assertTrue(jsonRes.containsValue("Please give valid longitude coordinate"));
        Assertions.assertTrue(jsonRes.containsValue("Please give valid latitude coordinate"));
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