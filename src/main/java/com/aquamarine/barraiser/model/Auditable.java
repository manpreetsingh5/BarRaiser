package com.aquamarine.barraiser.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;


@Getter
@Setter
@Accessors(chain = true)
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Auditable<U>
{
    @CreatedBy
    @Column(name = "created_by")
    private U createdBy;

    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate;

}
