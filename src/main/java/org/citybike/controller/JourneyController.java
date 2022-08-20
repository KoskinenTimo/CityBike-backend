package org.citybike.controller;

import org.citybike.dto.JourneyRequest;
import org.citybike.entity.Journey;
import org.citybike.exception.JourneyNotFoundException;
import org.citybike.exception.RequiredResourceNotFoundWithIdException;
import org.citybike.service.JourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/journeys")
public class JourneyController {

    @Autowired
    private JourneyService journeyService;

    @GetMapping("")
    public ResponseEntity<Page<Journey>> getJourneys(@RequestParam(required = false,defaultValue = "0") int page,
                                     @RequestParam(required = false) String filter,
                                     @RequestParam(required = false, defaultValue = "10") int journeysPerPage,
                                     @RequestParam(required = false) Long departureStationId,
                                     @RequestParam(required = false) Long returnStationId) {
        if (departureStationId != null) {
            return ResponseEntity.ok(journeyService.getAllJourneysByDepartureStationId(page,departureStationId));
        }
        if(returnStationId != null) {
            return ResponseEntity.ok(journeyService.getAllJourneysByReturnStationId(page,returnStationId));
        }
        if (filter != null && filter.length() > 0) {
            return ResponseEntity.ok(journeyService.getAllFilteredJourneys(page,journeysPerPage,filter));
        } else {
            return ResponseEntity.ok(journeyService.getAllJourneys(page,journeysPerPage));
        }
    }

    @PostMapping("")
    public ResponseEntity<Journey> postJourney(@RequestBody @Valid JourneyRequest journeyRequest)
            throws RequiredResourceNotFoundWithIdException {
        return new ResponseEntity<>(journeyService.saveJourney(journeyRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Journey> getOneJourney(@PathVariable Long id) throws JourneyNotFoundException {
        return ResponseEntity.ok(journeyService.getOneJourneyById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Journey> deleteJourney(@PathVariable Long id) throws JourneyNotFoundException {
        return ResponseEntity.ok(journeyService.removeJourney(id));
    }
}
