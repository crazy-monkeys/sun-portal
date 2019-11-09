package com.crazy.portal.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 23:39 2019-11-08
 * @Modified by:
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Test {

    @Id
    @Column(name ="id", unique=true)
    private long id;
    @Column(name ="firstName")
    private String firstName;
}
