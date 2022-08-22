package org.citybike.journeys;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.citybike.dto.JourneyRequest;
import org.citybike.entity.Journey;
import org.citybike.entity.Station;
import org.citybike.service.DataService;
import org.citybike.stations.StationControllerTest;
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


import java.sql.Timestamp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class JourneyControllerTest {
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
    public void getJourneys() throws Exception {
        MvcResult res = mockMvc.perform(get("/api/journeys"))
                .andReturn();

        String content = res.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("content"));
    }

    @Test
    public void postAndDeleteJourneySuccesfullyWithValidData() throws Exception {
        Long returnStationIdentifier = new Long(1001);
        Long departureStationIdentifier = new Long(1002);
        Station departureStation = StationControllerTest.createValidStation(returnStationIdentifier);
        Station returnStation = StationControllerTest.createValidStation(departureStationIdentifier);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(departureStation);
        mockMvc.perform(post("/api/stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isCreated());
        json = ow.writeValueAsString(returnStation);
        mockMvc.perform(post("/api/stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isCreated());

        JourneyRequest journey = createValidJourney(departureStationIdentifier,returnStationIdentifier);
        json = ow.writeValueAsString(journey);
        MvcResult res = mockMvc.perform(post("/api/journeys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isCreated())
                        .andReturn();

        String content = res.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("departureTimeStamp"));
        Assertions.assertTrue(content.contains("returnTimestamp"));
        Assertions.assertTrue(content.contains("departureStation"));
        Assertions.assertTrue(content.contains("returnStation"));
        Assertions.assertTrue(content.contains("distance"));
        Assertions.assertTrue(content.contains("duration"));

        JSONParser parser = new JSONParser();
        JSONObject jsonRes = (JSONObject) parser.parse(content);
        Long id;
        if (jsonRes.containsKey("id")) {
            id = (Long) jsonRes.get("id");
        } else {
            id = null;
        }
        Assertions.assertNotNull(id);

        mockMvc.perform(get("/api/journeys/" + id))
                .andExpect(status().isOk());
        mockMvc.perform(delete("/api/journeys/" + id))
                .andExpect(status().isOk())
                .andReturn();
        res = mockMvc.perform(get("/api/journeys/" + id))
                .andExpect(status().isNotFound())
                .andReturn();
        content = res.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("error"));
        Assertions.assertTrue(content.contains("Journey not found with id " + id));
    }

    @Test
    public void postJourneyWithBadDataAndReceiveCorrectErrors() throws Exception {
        Long returnStationIdentifier = new Long(2000);
        Long departureStationIdentifier = new Long(2001);
        JourneyRequest journey = new JourneyRequest();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(journey);
        MvcResult res = mockMvc.perform(post("/api/journeys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isBadRequest())
                        .andReturn();

        String content = res.getResponse().getContentAsString();
        JSONParser parser = new JSONParser();
        JSONObject jsonRes = (JSONObject) parser.parse(content);
        Assertions.assertTrue(jsonRes.containsKey("duration"));
        Assertions.assertTrue(jsonRes.containsKey("departureTimeStamp"));
        Assertions.assertTrue(jsonRes.containsKey("returnTimestamp"));
        Assertions.assertTrue(jsonRes.containsKey("returnStationIdentifier"));
        Assertions.assertTrue(jsonRes.containsKey("distance"));
        Assertions.assertTrue(jsonRes.containsKey("departureStationIdentifier"));
        Assertions.assertTrue(jsonRes.containsValue("must be greater than or equal to 10"));
        Assertions.assertTrue(jsonRes.containsValue("Departure timestamp is required"));
        Assertions.assertTrue(jsonRes.containsValue("Return timestamp is required"));
        Assertions.assertTrue(jsonRes.containsValue("Return station identifier is required"));
        Assertions.assertTrue(jsonRes.containsValue("must be greater than or equal to 10"));
        Assertions.assertTrue(jsonRes.containsValue("Departure station identifier is required"));

        journey = createValidJourney(departureStationIdentifier,returnStationIdentifier);
        json = ow.writeValueAsString(journey);
        res = mockMvc.perform(post("/api/journeys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isBadRequest())
                        .andReturn();

        content = res.getResponse().getContentAsString();
        jsonRes = (JSONObject) parser.parse(content);
        Assertions.assertTrue(jsonRes.containsKey("error"));
        Assertions.assertTrue(jsonRes.containsValue("Departure station not found with given identifier " + departureStationIdentifier));

        Station departureStation = StationControllerTest.createValidStation(departureStationIdentifier);
        Station returnStation = StationControllerTest.createValidStation(returnStationIdentifier);
        json = ow.writeValueAsString(departureStation);
        mockMvc.perform(post("/api/stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isCreated());
        json = ow.writeValueAsString(journey);
        res = mockMvc.perform(post("/api/journeys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andReturn();

        content = res.getResponse().getContentAsString();
        jsonRes = (JSONObject) parser.parse(content);
        Assertions.assertTrue(jsonRes.containsKey("error"));
        Assertions.assertTrue(jsonRes.containsValue("Return station not found with given identifier " + returnStationIdentifier));

        json = ow.writeValueAsString(returnStation);
        mockMvc.perform(post("/api/stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isCreated());
        journey.setDepartureTimeStamp(DataService.parseISODateStringToTimestamp("2021-06-30T21:04:32.000+00:00"));
        journey.setReturnTimestamp(DataService.parseISODateStringToTimestamp("2021-06-30T21:00:04.000+00:000"));
        json = ow.writeValueAsString(journey);
        res = mockMvc.perform(post("/api/journeys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isBadRequest())
                        .andReturn();

        content = res.getResponse().getContentAsString();
        jsonRes = (JSONObject) parser.parse(content);
        Assertions.assertTrue(jsonRes.containsKey("error"));
        Assertions.assertTrue(jsonRes.containsValue("Return timestamp cannot be greater than departure timestamp"));
    }

    public JourneyRequest createValidJourney(Long departureStationIdentifier, Long returnStationIdentifier) {
        return JourneyRequest.build(
                DataService.parseISODateStringToTimestamp("2021-06-30T21:00:04.000+00:00"),
                DataService.parseISODateStringToTimestamp("2021-06-30T21:04:32.000+00:00"),
                departureStationIdentifier,
                returnStationIdentifier,
                100,
                200);
    }
}