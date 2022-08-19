package org.citybike.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.sql.Timestamp;

@Data
@Table(name = "JOURNEYS_TBL")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Journey extends AbstractPersistable<Long> {

    private Timestamp departureTimeStamp;
    private Timestamp returnTimestamp;
    @OneToOne
    @JoinColumn(name = "departureStation")
    private Station departureStation;
    @OneToOne
    @JoinColumn(name="returnStation")
    private Station returnStation;
    private double distance;
    private double duration;

}
