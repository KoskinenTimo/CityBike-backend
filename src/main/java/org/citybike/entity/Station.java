package org.citybike.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;


@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "STATIONS_TBL", uniqueConstraints = { @UniqueConstraint(columnNames = { "identifier" }) })
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
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
    private int kapasiteetit = 0;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "location_ID")
    private Location location;
}
