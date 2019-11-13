package com.crazy.portal.repository;

import com.crazy.portal.entity.OperationLogDO;
import org.springframework.data.repository.CrudRepository;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 23:32 2019-09-15
 * @Modified by:
 */
public interface OperationLogRepository extends CrudRepository<OperationLogDO,Integer> {

    OperationLogDO findByCookie(String cookie);

}
