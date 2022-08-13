package org.citybike;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class JourneyService {

    @Autowired
    private JourneyRepository journeyRepository;

    public Page<Journey> getAllJourneys(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 50, Sort.by("id").descending());
        return journeyRepository.findAll(pageable);
    }

    public Page<Journey> getAllFilteredJourneys(int pageNumber, String filter) {
        Pageable pageable = PageRequest.of(pageNumber, 50, Sort.by("id").descending());
        return journeyRepository.findAllByStationName(filter,pageable);
    }
}
