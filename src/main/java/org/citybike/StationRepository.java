package org.citybike;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StationRepository extends JpaRepository<Station,Long> {
    Page<Station> findByName(String name, Pageable pageable);
}
