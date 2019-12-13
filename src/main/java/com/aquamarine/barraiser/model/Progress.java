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

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(name = "progress_to_cohorts",
            joinColumns = @JoinColumn(name = "progress_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "cohort_id", referencedColumnName = "id"))
    private Set<Cohort> cohorts = new HashSet<>();

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(name = "progress_to_drinks",
            joinColumns = @JoinColumn(name = "progress_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "drink_id", referencedColumnName = "id"))
    private Set<Drink> drinks = new HashSet<>();

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(name = "progress_to_users",
            joinColumns = @JoinColumn(name = "progress_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();

    @Column(nullable = false)
    private int status;
}
