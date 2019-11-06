package com.aquamarine.barraiser.model;

import com.aquamarine.barraiser.enums.UserEnum;
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
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @Column(name = "password", nullable = false)
    private String hash_pass;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private UserEnum status;

}
