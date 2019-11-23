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
public class Step extends Auditable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;

    @Column(nullable = false)
    private Integer step_number;

    @Column(nullable = false)
    private String description;

    @OneToOne
    @JoinColumn(name = "equipment_id", referencedColumnName = "id")
    private Equipment equipment;

    @Column(nullable = false)
    private Integer quantity;

    @Column(unique = true, nullable = false)
    private String measurement;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "cohort_to_users",
            joinColumns = @JoinColumn(name = "cohort_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> user = new HashSet<>();
}
