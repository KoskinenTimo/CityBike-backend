package org.citybike.service;

import org.citybike.dto.StationRequest;
import org.citybike.entity.Location;
import org.citybike.entity.Station;
import org.citybike.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;


    public Page<Station> getAllStations(int pageNumber, int stationsPerPage) {
        Pageable pageable = PageRequest.of(pageNumber, stationsPerPage, Sort.by("id").descending());
        return stationRepository.findAll(pageable);
    }
    public Page<Station> getAllStationsWithName(int pageNumber, int stationsPerPage, String filter) {
        Pageable pageable = PageRequest.of(pageNumber, stationsPerPage, Sort.by("id").descending());
        return stationRepository.findAllByStationName(filter,pageable);
    }

    public Station getOneStationById(Long id) {
        return stationRepository.findByIdentifier(id);
    }

    public Station saveStation(StationRequest stationRequest) {
        Location location = Location.build(
                stationRequest.getLatitude(),
                stationRequest.getLongitude());

        Station station = Station.build(
                stationRequest.getIdentifier(),
                stationRequest.getNimi(),
                stationRequest.getNamn(),
                stationRequest.getName(),
                stationRequest.getOsoite(),
                stationRequest.getAdress(),
                stationRequest.getKaupunki(),
                stationRequest.getStad(),
                stationRequest.getOperaattori(),
                stationRequest.getKapasiteetit(),
                location);

        return stationRepository.save(station);
    }

    public Station removeStation(Long id) {
        Station station = stationRepository.findByIdentifier(id);
        stationRepository.deleteById(station.getId());
        return station;
    }
}
