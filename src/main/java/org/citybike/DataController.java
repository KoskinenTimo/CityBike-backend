package org.citybike;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataController {

    @Autowired

    private DataService dataService;


    @GetMapping("/seeddb")
    public void seedDb() {
        dataService.addCSVStationsDataToDb();
        dataService.addCSVJourneysToDb();
    }

    @GetMapping("/resetdb")
    public void resetDb() {
        dataService.resetDB();
    }

    @GetMapping("/testseed/stations/page/{page}")
    public Page<Station> getStations(@PathVariable int page) {
        return dataService.getAllStations(page);
    }

    @GetMapping("/testseed/journeys/page/{page}")
    public Page<Journey> getJourneys(@PathVariable int page) {
        return dataService.getAllJourneys(page);
    }
}