package org.citybike;

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

    public Station getOneStationById(Long id) {
        return stationRepository.findByIdentifier(id);
    }
}
