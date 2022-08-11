package org.citybike;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Station {

    private Long id;
    private Long fid;
    private String nimi;
    private String namn;
    private String name;
    private String osoite;
    private String adress;
    private String kaupunki;
    private String stad;
    private String Operaattori;
    private int kapasiteetit;
    private Location location;




}
