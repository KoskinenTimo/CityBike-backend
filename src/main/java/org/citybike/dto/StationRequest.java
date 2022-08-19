package org.citybike.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class StationRequest {

    @NotNull(message = "Identifier is required")
    @Min(value = 1, message = "Identifier must be greater than 0")
    private Long identifier;

    @NotBlank(message = "Nimi is required")
    private String nimi;

    private String namn = "";

    private String name = "";

    @NotBlank(message = "Osoite is required")
    private String osoite;

    private String adress = "";

    @NotBlank(message = "Kaupunki is required")
    private String kaupunki;

    private String stad = "";

    private String operaattori = "";

    @NotNull(message = "Kapasiteetit is required")
    @Min(value = 1, message = "Kapasiteetit must be greater than zero")
    private int kapasiteetit;

    @NotNull(message = "Location is required")
    @Valid
    private LocationRequest location;

}
