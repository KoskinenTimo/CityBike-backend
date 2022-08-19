package org.citybike.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.citybike.service.DataService;


@CrossOrigin
@RestController
public class DataController {

    @Autowired

    private DataService dataService;


    @GetMapping("/api/seeddb")
    public void seedDb() {
        dataService.addCSVStationsDataToDb();
        //dataService.addCSVJourneysToDb();
    }

}
