package com.crazy.portal.service.maintenance;

import com.crazy.portal.dao.maintenance.*;
import com.crazy.portal.entity.maintenance.SunProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName: MaintenanceCreateService
 * @Author: God Man Qiu~
 * @Date: 2019/10/14 23:10
 */
@Slf4j
@Service
public class MaintenanceCreateService {
    @Resource
    private SunMaintenanceMapper sunMaintenanceMapper;
    @Resource
    private SunProductMapper sunProductMapper;
    @Resource
    private SunContactMapper sunContactMapper;
    @Resource
    private SunAddressMapper sunAddressMapper;
    @Resource
    private SunFilesMapper sunFilesMapper;



}
