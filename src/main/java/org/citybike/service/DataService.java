package org.citybike.service;

import org.citybike.entity.Journey;
import org.citybike.repository.JourneyRepository;
import org.citybike.entity.Location;
import org.citybike.repository.LocationRepository;
import org.citybike.entity.Station;
import org.citybike.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private JourneyRepository journeyRepository;

    @Autowired
    private LocationRepository locationRepository;

    private List<Journey> journeys = new ArrayList<>();

    @Transactional
    public void addCSVStationsDataToDb() {
        try {
            FileInputStream fileInputStream = new FileInputStream(
                    "./src/main/resources/public/data/Helsingin_ja_Espoon_kaupunkipyöräasemat_avoin.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            List<Station> stations = new ArrayList<>();
            String line;
            bufferedReader.readLine();
            while((line = bufferedReader.readLine()) != null) {
                String[] stationDetailsInList = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                Station stationObject = createStationFromDetailsList(stationDetailsInList);
                stations.add(stationObject);
            }

            System.out.println("Stations added: " + stations.size());
            if (stations.size() != 0) {
                stationRepository.saveAll(stations);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCSVJourneysToDb() {
        File journeysFolder = new File("./src/main/resources/public/data/journeys");
        File[] listOfJourneyFiles = journeysFolder.listFiles();
        readMultipleFilesToList(listOfJourneyFiles);
        System.out.println("Saving all journeys to DB");
        journeyRepository.saveAll(journeys);
        System.out.println("Saving journeys to DB done");
    }

    public void readMultipleFilesToList(File[] files) {
        for (int i = 0; i < files.length; i++) {
            readFileToList(files[i].getName());
        }
    }

    public void readFileToList(String fileName) {
        try {
            FileInputStream fileInputStream = new FileInputStream(
                    "./src/main/resources/public/data/journeys/" + fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            bufferedReader.readLine();
            System.out.println("Adding journeys from file: " + fileName);
            while((line = bufferedReader.readLine()) != null) {
                String[] journeyDetailsInList = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                double distance = parseDouble(journeyDetailsInList[6]);
                double duration = parseDouble(journeyDetailsInList[7]);

                if (distance > 10 && duration > 10) {
                    Journey journey = createJourneyFromDetailsList(journeyDetailsInList);
                    journeys.add(journey);
                }
            }
            System.out.println("Journeys ready to move to DB: " + journeys.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Station createStationFromDetailsList(String[] stationDetailsInList) {
        Station stationObject = new Station();
        Location locationObject = new Location();
        stationObject.setIdentifier(Long.parseLong(stationDetailsInList[1]));
        stationObject.setNimi(stationDetailsInList[2]);
        stationObject.setNamn(stationDetailsInList[3]);
        stationObject.setName(stationDetailsInList[4]);
        stationObject.setOsoite(stationDetailsInList[5]);
        stationObject.setAdress(stationDetailsInList[6]);
        stationObject.setKaupunki(stationDetailsInList[7]);
        stationObject.setStad(stationDetailsInList[8]);
        stationObject.setOperaattori(stationDetailsInList[9]);
        stationObject.setKapasiteetit(parseInt(stationDetailsInList[10]));
        locationObject.setLatitude(stationDetailsInList[11]);
        locationObject.setLongitude(stationDetailsInList[12]);
        stationObject.setLocation(locationObject);
        return stationObject;
    }

    public Journey createJourneyFromDetailsList(String[] journeyDetailsInList) {
        Journey journey = new Journey();
        journey.setDepartureTimeStamp(parseISODateStringToTimestamp(journeyDetailsInList[0]));
        journey.setReturnTimestamp(parseISODateStringToTimestamp(journeyDetailsInList[1]));

        Station departureStation = stationRepository.findByIdentifier(Long.parseLong(journeyDetailsInList[2]));
        journey.setDepartureStation(departureStation);

        Station returnStation = stationRepository.findByIdentifier(Long.parseLong(journeyDetailsInList[4]));
        journey.setReturnStation(returnStation);

        journey.setDistance(parseDouble(journeyDetailsInList[6]));
        journey.setDuration(parseDouble(journeyDetailsInList[7]));
        return journey;
    }
    public Timestamp parseISODateStringToTimestamp(String dateString) {
        Instant instant = null;
        try {
            instant = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .parse(dateString)
                    .toInstant();
        } catch (ParseException e) {
            return null;
        }
        return Timestamp.from(instant);
    }

    public Page<Journey> getAllJourneys(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 50, Sort.by("id").descending());
        return journeyRepository.findAll(pageable);
    }

    public double parseDouble(String str) {
        if (str != null && str.length() > 0) {
            try {
                return Double.parseDouble(str);
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }

    public int parseInt(String str) {
        if (str != null && str.length() > 0) {
            try {
                return Integer.parseInt(str);
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }

}
