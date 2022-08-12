package org.citybike;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DataService {

    @Autowired
    private StationRepository stationRepository;

    @Transactional
    public void addCSVStationsDataToDb() {
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader("./src/main/resources/public/data/Helsingin_ja_Espoon_kaupunkipyöräasemat_avoin.csv"));
            List<Station> stations = new ArrayList<>();
            String line;
            bufferedReader.readLine();
            while((line = bufferedReader.readLine()) != null) {
                String[] stationDetailsInList = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                Long currentStationId = Long.parseLong(stationDetailsInList[1]);
                Optional<Station> foundStation = stationRepository.findById(currentStationId);
                System.out.println(line);
                if (!foundStation.isPresent()) {
                    Station stationObject = new Station();
                    Location locationObject = new Location();
                    stationObject.setId(currentStationId);
                    stationObject.setNimi(stationDetailsInList[2]);
                    stationObject.setNamn(stationDetailsInList[3]);
                    stationObject.setName(stationDetailsInList[4]);
                    stationObject.setOsoite(stationDetailsInList[5]);
                    stationObject.setAdress(stationDetailsInList[6]);
                    stationObject.setKaupunki(stationDetailsInList[7]);
                    stationObject.setStad(stationDetailsInList[8]);
                    stationObject.setOperaattori(stationDetailsInList[9]);
                    stationObject.setKapasiteetit(Integer.parseInt(stationDetailsInList[10]));
                    locationObject.setLatitude(Double.parseDouble(stationDetailsInList[11]));
                    locationObject.setLongitude(Double.parseDouble(stationDetailsInList[12]));
                    stationObject.setLocation(locationObject);
                    stations.add(stationObject);

                }
            }
            if (stations.size() != 0) {
                stationRepository.saveAll(stations);
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public Page<Station> getAllStations(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 50, Sort.by("id").descending());
        return stationRepository.findAll(pageable);

    }
}
