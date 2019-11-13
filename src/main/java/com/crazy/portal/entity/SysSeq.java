package com.crazy.portal.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName: SysSeq
 * @Author: God Man Qiu~
 * @Date: 2019/11/12 14:11
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SysSeq {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name ="seq_model")
    private String seqModel;

    @Column(name ="seq_day")
    private String seqDay;

    @Column(name ="seq_value")
    private Integer seqValue;

    @Column(name ="create_time")
    private Date createTime;

    @Column(name ="update_time")
    private Date updateTime;
}
