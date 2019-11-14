package com.aquamarine.barraiser.model;

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
public class Cohort {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String description;

    @OneToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "id")
    private User instructor;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "cohort_to_users",
            joinColumns = @JoinColumn(name = "cohort_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> user = new HashSet<>();
}
