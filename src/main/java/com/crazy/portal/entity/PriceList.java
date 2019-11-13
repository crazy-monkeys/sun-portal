package com.crazy.portal.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author Bill
 * @date   2019-10-24 00:42::15
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PriceList {
    @Id
    private Integer id;

    @Column(name ="model")
    private String model;

    @Column(name ="part_early_bird_discount")
    private BigDecimal partEarlyBirdDiscount;

    @Column(name ="standard_early_bird_discount")
    private BigDecimal standardEarlyBirdDiscount;

    @Column(name ="part_standard")
    private BigDecimal partStandard;

    @Column(name ="standard_standard")
    private BigDecimal standardStandard;

    @Column(name ="active")
    private Integer active;

    @Column(name ="create_time")
    private Date createTime;

    @Column(name ="update_time")
    private Date updateTime;
}