package org.citybike.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "LOCATIONS_TBL")
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Entity
public class Location extends AbstractPersistable<Long> {

    private String latitude;
    private String longitude;
}
