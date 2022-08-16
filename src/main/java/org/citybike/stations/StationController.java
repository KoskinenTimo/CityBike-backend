package org.citybike.stations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class StationController {

    @Autowired
    private StationService stationService;


    @GetMapping("/api/stations")
    public Page<Station> getStations(@RequestParam(required = false, defaultValue = "0") Integer page,
                                     @RequestParam(required = false) String filter,
                                     @RequestParam(required = false, defaultValue = "10") Integer stationsPerPage) {
        if (filter != null && filter.length() > 0) {
            return stationService.getAllStationsWithName(page,stationsPerPage,filter);
        }
        return stationService.getAllStations(page,stationsPerPage);
    }

    @GetMapping("/api/stations/{id}")
    public Station getStation(@PathVariable Long id) {
        return stationService.getOneStationById(id);
    }

    @PostMapping("/api/stations")
    public Station postStation(@RequestBody Station station) {
        return stationService.saveStation(station);
    }

    @DeleteMapping("/api/stations/{id}")
    public Station deleteStation(@PathVariable Long id) {
        return stationService.removeStation(id);
    }
}
