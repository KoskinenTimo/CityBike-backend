package org.citybike;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class JourneyController {

    @Autowired
    private JourneyService journeyService;

    @GetMapping("/journeys")
    public Page<Journey> getJourneys(@RequestParam(required = false,defaultValue = "0") int page,
                                     @RequestParam(required = false) String filter,
                                     @RequestParam(required = false, defaultValue = "10") int journeysPerPage,
                                     @RequestParam(required = false) Long departureStationId,
                                     @RequestParam(required = false) Long returnStationId) {
        if (departureStationId != null) {
            return journeyService.getAllJourneysByDepartureStationId(page,departureStationId);
        }
        if(returnStationId != null) {
            return journeyService.getAllJourneysByReturnStationId(page,returnStationId);
        }
        if (filter != null && filter.length() > 0) {
            return journeyService.getAllFilteredJourneys(page,journeysPerPage,filter);
        } else {
            return journeyService.getAllJourneys(page,journeysPerPage);
        }
    }
}
