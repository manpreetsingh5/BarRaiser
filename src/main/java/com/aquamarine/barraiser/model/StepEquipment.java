package com.aquamarine.barraiser.model;

import com.aquamarine.barraiser.enums.MeasurementEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class StepEquipment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "step_id", referencedColumnName = "id")
    @JsonIgnore
    private Step step;

    @OneToOne
    @JoinColumn(name = "equipment_id", referencedColumnName = "id")
    private Equipment equipment;

    @Column(nullable = false)
    private double quantity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MeasurementEnum unit;
}
