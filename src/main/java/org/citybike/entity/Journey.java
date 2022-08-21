package org.citybike.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.sql.Timestamp;

@NamedEntityGraph(
        name = "journey-entity-graph-with-stations-location",
        attributeNodes = {
                @NamedAttributeNode(value = "departureStation", subgraph = "departureStation-subgraph"),
                @NamedAttributeNode(value = "returnStation", subgraph = "returnStation-subgraph"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "departureStation-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("location")
                        }
                ),
                @NamedSubgraph(
                        name = "returnStation-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("location")
                        }
                )
        }
)
@Data
@Table(name = "JOURNEYS_TBL")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
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
