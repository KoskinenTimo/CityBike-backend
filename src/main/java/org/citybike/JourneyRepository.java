package org.citybike;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JourneyRepository extends JpaRepository<Journey,Long> {
    //Page<Journey> findAllBydepartureStationIdNimiOrdepartureStationIdNamnContaining(String filter, Pageable pageable);
    @Query(value="select Journey from #{#entityName} Journey where " +
            "UPPER(Journey.departureStationId.nimi) like %?#{[0].toUpperCase()}% " +
            "OR UPPER(Journey.departureStationId.namn) like %?#{[0].toUpperCase()}% " +
            "OR UPPER(Journey.departureStationId.name) like %?#{[0].toUpperCase()}%")
    Page<Journey> findAllByStationName(@Param("filter") String filter, Pageable pageable);

    @Query(value="select Journey from #{#entityName} Journey where " +
            "Journey.departureStationId.identifier = :departureStationId")
    Page<Journey> findAllByDepartureStationId(@Param("departureStationId") Long departureStationId, Pageable pageable);

    @Query(value="select Journey from #{#entityName} Journey where " +
            "Journey.returnStationId.identifier = :returnStationId")
    Page<Journey> findAllByReturnStationId(@Param("returnStationId") Long returnStationId, Pageable pageable);
}
