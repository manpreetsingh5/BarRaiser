package com.aquamarine.barraiser.model;

import lombok.*;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // This tells Hibernate to make a table out of this class
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean status;

    @ManyToMany(cascade = CascadeType.DETACH, mappedBy = "user")
    private Set<Cohort> cohort = new HashSet<>();
}