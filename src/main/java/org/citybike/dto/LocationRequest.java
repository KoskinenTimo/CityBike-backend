package org.citybike.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class LocationRequest {

    @NotNull(message = "Latitude is required")
    @Pattern(regexp = "^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,6})?))$",
            message = "Please give valid longitude coordinate")
    private String latitude;

    @NotNull(message = "Latitude is required")
    @Pattern(regexp = "^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,6})?))$",
            message = "Please give valid latitude coordinate")
    private String longitude;
}
