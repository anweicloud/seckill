package com.anwei.dao;

import com.anwei.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class SuccessKilledDaoTest {

    @Resource
    SuccessKilledDao successKilledDao;

    @Test
    public void saveSuccessKilled() {
        int count = successKilledDao.saveSuccessKilled(100, 18286176420L);
        System.out.println(count);
    }


    @Test
    public void queryByIdWithSeckill() {
        SuccessKilled sk = successKilledDao.queryByIdWithSeckill(100, 18286176420L);
        System.out.println(sk);
    }
}