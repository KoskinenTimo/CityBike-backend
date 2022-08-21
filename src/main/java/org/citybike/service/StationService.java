package org.citybike.service;

import org.citybike.dto.StationRequest;
import org.citybike.entity.Location;
import org.citybike.entity.Station;
import org.citybike.repository.StationRepository;
import org.citybike.exception.StationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public Station getOneStationById(Long id) throws StationNotFoundException {
        Station station = stationRepository.findByIdentifier(id);
        if (station == null) {
            throw new StationNotFoundException("Station not found with id " + id);
        }
        return station;
    }

    public Station saveStation(StationRequest stationRequest) {
        Location location = Location.build(
                stationRequest.getLocation().getLatitude(),
                stationRequest.getLocation().getLongitude());

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

    public Station removeStation(Long id) throws StationNotFoundException {
        Station station = stationRepository.findByIdentifier(id);
        if (station == null) {
            throw new StationNotFoundException("Station not found with id " + id);
        }
        stationRepository.deleteById(station.getId());
        return station;
    }
}
