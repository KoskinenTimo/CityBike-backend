package org.citybike.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class LocationRequest {

    @NotNull(message = "Latitude is required")
    private double latitude;

    @NotNull(message = "Latitude is required")
    private double longitude;
}
