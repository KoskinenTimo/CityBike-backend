package org.citybike.journeys;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.citybike.stations.Station;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.sql.Timestamp;

@Data
@Table(name = "Journey")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Journey extends AbstractPersistable<Long> {

    private Timestamp departureTimeStamp;
    private Timestamp returnTimestamp;
    @OneToOne
    @JoinColumn(name = "departureStationId")
    private Station departureStationId;
    @OneToOne
    @JoinColumn(name="returnStationId")
    private Station returnStationId;
    private double distance;
    private double duration;

}
