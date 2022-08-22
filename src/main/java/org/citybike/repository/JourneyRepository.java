package org.citybike.repository;

import org.apache.ibatis.annotations.Param;
import org.citybike.entity.Journey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JourneyRepository extends JpaRepository<Journey,Long> {
    @Query(value="select Journey from #{#entityName} Journey where " +
            "UPPER(Journey.departureStation.nimi) like %?#{[0].toUpperCase()}% " +
            "OR UPPER(Journey.departureStation.namn) like %?#{[0].toUpperCase()}% " +
            "OR UPPER(Journey.departureStation.name) like %?#{[0].toUpperCase()}%")
    Page<Journey> findAllByStationName(@Param("filter") String filter, Pageable pageable);

    @Query(value="select Journey from #{#entityName} Journey where " +
            "Journey.departureStation.identifier = :departureStationId")
    Page<Journey> findAllByDepartureStationId(@Param("departureStationId") Long departureStationId, Pageable pageable);

    @Query(value="select Journey from #{#entityName} Journey where " +
            "Journey.returnStation.identifier = :returnStationId")
    Page<Journey> findAllByReturnStationId(@Param("returnStationId") Long returnStationId, Pageable pageable);

    @EntityGraph(value = "journey-entity-graph-with-stations-location", type = EntityGraph.EntityGraphType.LOAD)
    Page<Journey> findAll(Pageable pageable);
}
