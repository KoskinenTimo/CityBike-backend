package org.citybike.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.citybike.dto.constraint.ValidDateRange;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.sql.Timestamp;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@ValidDateRange
public class JourneyRequest {

    @NotNull(message = "Departure timestamp is required")
    @Past(message = "Departure timestamp must be in the past")
    private Timestamp departureTimeStamp;

    @NotNull(message = "Return timestamp is required")
    @Past(message = "Return timestamp must be in the past")
    private Timestamp returnTimestamp;

    @NotNull(message = "Departure station identifier is required")
    private Long departureStationIdentifier;

    @NotNull(message = "Return station identifier is required")
    private Long returnStationIdentifier;

    @NotNull(message = "Distance is required")
    @Min(10)
    private double distance;

    @NotNull(message = "Duration is required")
    @Min(10)
    private double duration;


}
