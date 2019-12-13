package com.aquamarine.barraiser.model;

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
public class Progress {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private Cohort cohort;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private Drink drink;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private boolean status;
}
