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
@Entity
public class Drink extends Auditable<String>{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(unique = true, nullable = false)
    private String image_path;

    @Column(nullable = false)
    private boolean isPublic;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "drink_to_steps",
            joinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "drink_id", referencedColumnName = "id"))
    private Set<Step> steps = new HashSet<>();


}
