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


    public Page<Station> getAllStations(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 50, Sort.by("id").descending());
        return stationRepository.findAll(pageable);
    }
    public Page<Station> getAllStationsWithName(int pageNumber,String name) {
        Pageable pageable = PageRequest.of(pageNumber, 50, Sort.by("id").descending());
        return stationRepository.findByName(name,pageable);
    }

    public Station getOneStationById(Long id) {
        return stationRepository.findById(id).orElse(null);
    }
}
