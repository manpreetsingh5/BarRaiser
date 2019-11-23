package com.aquamarine.barraiser.model;

import com.aquamarine.barraiser.enums.EquipmentEnum;
import lombok.*;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Equipment extends Auditable<String> {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String description;

    @Column(unique = true, nullable = false)
    private String image_path;

    @Column(nullable = false)
    private boolean isPublic;

    @Column
    private EquipmentEnum type;

}