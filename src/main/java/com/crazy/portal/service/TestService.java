package com.crazy.portal.service;

import com.crazy.portal.entity.Test;
import com.crazy.portal.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 23:41 2019-11-08
 * @Modified by:
 */
@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public long getCount() {
        long count = testRepository.count();
        return count;
    }

    public boolean insertTest() {
        try {
            Test test = new Test();
            test.setId(1L);
            test.setFirstName("jack");
            testRepository.save(test);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
