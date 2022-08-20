package org.citybike.service;


import org.citybike.dto.JourneyRequest;
import org.citybike.entity.Journey;
import org.citybike.entity.Station;
import org.citybike.exception.JourneyNotFoundException;
import org.citybike.exception.RequiredResourceNotFoundWithIdException;
import org.citybike.repository.JourneyRepository;
import org.citybike.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JourneyService {

    @Autowired
    private JourneyRepository journeyRepository;

    @Autowired
    private StationRepository stationRepository;

    public Page<Journey> getAllJourneys(int pageNumber, int journeysPerPage) {
        Pageable pageable = PageRequest.of(pageNumber, journeysPerPage, Sort.by("id").descending());
        return journeyRepository.findAll(pageable);
    }

    public Page<Journey> getAllFilteredJourneys(int pageNumber, int journeysPerPage, String filter) {
        Pageable pageable = PageRequest.of(pageNumber, journeysPerPage, Sort.by("id").descending());
        return journeyRepository.findAllByStationName(filter,pageable);
    }

    public Page<Journey> getAllJourneysByDepartureStationId(int pageNumber, Long departureStationId) {
        Pageable pageable = PageRequest.of(pageNumber, 50, Sort.by("id").descending());
        return journeyRepository.findAllByDepartureStationId(departureStationId,pageable);
    }

    public Page<Journey> getAllJourneysByReturnStationId(int pageNumber, Long returnStationId) {
        Pageable pageable = PageRequest.of(pageNumber, 50, Sort.by("id").descending());
        return journeyRepository.findAllByReturnStationId(returnStationId,pageable);
    }

    public Journey getOneJourneyById(Long id) throws JourneyNotFoundException {
        Optional<Journey> journey = journeyRepository.findById(id);
        if (!journey.isPresent()) {
            throw new JourneyNotFoundException("Journey not found with id " + id);
        }
        return journey.get();
    }

    public Journey saveJourney(JourneyRequest journeyRequest) throws RequiredResourceNotFoundWithIdException {
        Long departureStationIdentifier = journeyRequest.getDepartureStationIdentifier();
        Station departureStation = stationRepository.findByIdentifier(departureStationIdentifier);
        Long returnStationIdentifier = journeyRequest.getReturnStationIdentifier();
        Station returnStation = stationRepository.findByIdentifier(returnStationIdentifier);

        if (departureStation == null) {
            throw new RequiredResourceNotFoundWithIdException(
                    "Departure station not found with given identifier " + departureStationIdentifier);
        }
        if (returnStation == null) {
            throw new RequiredResourceNotFoundWithIdException(
                    "Return station not found with given identifier " + returnStationIdentifier);
        }

        Journey journey = Journey.build(
                journeyRequest.getDepartureTimeStamp(),
                journeyRequest.getReturnTimestamp(),
                departureStation,
                returnStation,
                journeyRequest.getDistance(),
                journeyRequest.getDuration());

        return journeyRepository.save(journey);
    }

    public Journey removeJourney(Long id) throws JourneyNotFoundException {
        Optional<Journey> journey = journeyRepository.findById(id);
        if (!journey.isPresent()) {
            throw new JourneyNotFoundException("Journey not found with id " + id);
        }
        journeyRepository.deleteById(id);
        return journey.get();
    }
}
