package org.citybike;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "Station")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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
