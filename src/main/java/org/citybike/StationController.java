package org.citybike;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;



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

}
