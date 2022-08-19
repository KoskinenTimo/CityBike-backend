package org.citybike.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.citybike.entity.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class StationRequest {

    @NotBlank(message = "You must give a valid identifier")
    private Long identifier;

    @NotBlank(message = "You must give a valid nimi")
    private String nimi;

    private String namn = "";

    private String name = "";

    @NotBlank(message = "You must give a valid osoite")
    private String osoite;

    private String adress = "";

    @NotBlank(message = "You must give a valid kaupunki")
    private String kaupunki;

    private String stad = "";

    private String operaattori = "";

    @NotBlank(message = "You must give a valid kapasiteetit")
    private int kapasiteetit = 0;

    @NotNull
    private Location location;

    @NotBlank
    private double latitude;

    @NotBlank
    private double longitude;
}
