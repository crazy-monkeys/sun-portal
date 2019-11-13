package com.crazy.portal.repository;

import com.crazy.portal.entity.PriceList;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @ClassName: PriceListRepository
 * @Author: God Man Qiu~
 * @Date: 2019/11/12 18:39
 */
public interface PriceListRepository extends CrudRepository<PriceList,Integer> {
    PriceList findByModel(String productModel);
    List<PriceList> findAllByActive(String active);
}
