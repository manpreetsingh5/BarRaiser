//package com.aquamarine.barraiser.model;
//
//import lombok.*;
//import lombok.experimental.Accessors;
//import org.joda.time.DateTime;
//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.CreatedDate;
//
//import javax.persistence.*;
//import java.util.HashSet;
//import java.util.Set;
//
//@Getter
//@Setter
//@Accessors(chain = true)
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Entity
//public class Equipment {
//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
//    private Integer id;
//
//    @Column(unique = true, nullable = false)
//    private String description;
//
//    @Column(unique = true, nullable = false)
//    private String image_path;
//
//    @CreatedBy
//    private User added_by;
//
//    @CreatedDate
//    private DateTime date_created;
//
//    @ManyToMany(cascade = CascadeType.DETACH)
//    @JoinTable(name = "cohort_to_users",
//            joinColumns = @JoinColumn(name = "cohort_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
//    private Set<User> user = new HashSet<>();
//}