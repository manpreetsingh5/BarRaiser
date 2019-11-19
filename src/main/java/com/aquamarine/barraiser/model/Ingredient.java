//package com.aquamarine.barraiser.model;
//
//import lombok.*;
//import lombok.experimental.Accessors;
//import org.springframework.data.annotation.CreatedDate;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Getter
//@Setter
//@Accessors(chain = true)
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Entity
//public class Ingredient {
//
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
//    @CreatedDate
//    @Column (name = "created date")
//    private Date createdDate;
//
//}
