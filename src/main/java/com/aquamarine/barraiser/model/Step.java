package com.aquamarine.barraiser.model;

import com.aquamarine.barraiser.enums.ActionsEnum;
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
public class Step {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "drink_id", referencedColumnName = "id")
    @JsonIgnore
    private Drink drink;

    @Column(nullable = false)
    private Integer step_number;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActionsEnum action;

    @OneToMany(mappedBy="step")
    private Set<StepEquipment> equipmentSet;
}
