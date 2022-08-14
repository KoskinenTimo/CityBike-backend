package org.citybike.stations;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.citybike.locations.Location;
import org.springframework.data.jpa.domain.AbstractPersistable;


@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "Station")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Station extends AbstractPersistable<Long> {

    private Long identifier;
    private String nimi;
    private String namn;
    private String name;
    private String osoite;
    private String adress;
    private String kaupunki;
    private String stad;
    private String operaattori;
    private int kapasiteetit;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "location_ID")
    private Location location;
}
