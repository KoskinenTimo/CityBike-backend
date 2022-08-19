package org.citybike.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.citybike.entity.Station;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.sql.Timestamp;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class JourneyRequest {

    private Timestamp departureTimeStamp;
    private Timestamp returnTimestamp;
    private Station departureStation;
    private Station returnStation;
    private double distance;
    private double duration;
}
