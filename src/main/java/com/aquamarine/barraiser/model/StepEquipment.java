package com.aquamarine.barraiser.model;

import com.aquamarine.barraiser.enums.MeasurementEnum;
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
    private Step step;

    @Column(nullable = false)
    private double quantity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MeasurementEnum unit;
}
