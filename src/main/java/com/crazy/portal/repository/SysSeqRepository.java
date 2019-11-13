package com.crazy.portal.repository;

import com.crazy.portal.entity.SysSeq;
import org.springframework.data.repository.CrudRepository;

/**
 * @ClassName: SysSeqRepository
 * @Author: God Man Qiu~
 * @Date: 2019/11/12 14:13
 */
public interface SysSeqRepository extends CrudRepository<SysSeq,Integer> {
    SysSeq findBySeqDayAndSeqModel(String day, String model);
}
