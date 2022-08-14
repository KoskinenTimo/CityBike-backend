package org.citybike;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StationRepository extends JpaRepository<Station,Long> {
    @Query(value="select Station from #{#entityName} Station where " +
            "UPPER(Station.nimi) like %?#{[0].toUpperCase()}% " +
            "OR UPPER(Station.namn) like %?#{[0].toUpperCase()}% " +
            "OR UPPER(Station.name) like %?#{[0].toUpperCase()}%")
    Page<Station> findAllByStationName(@Param("filter") String filter, Pageable pageable);

    Station findByIdentifier(Long identifier);
}
