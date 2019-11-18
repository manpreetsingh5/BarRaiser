package com.aquamarine.barraiser.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String step;

    @Column(unique = true, nullable = false)
    private Integer step_number;

}
