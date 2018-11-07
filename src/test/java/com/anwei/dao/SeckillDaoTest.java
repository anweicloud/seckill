package com.anwei.dao;

import com.anwei.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class SeckillDaoTest {

    @Resource
    SeckillDao seckillDao;

    @Test
    public void reduceNumber() {
        int n = seckillDao.reduceNumber(100, new Date());
        System.out.println("count:" + n);
    }

    @Test
    public void queryById() {
        Seckill seckill = seckillDao.queryById(100);
        System.out.print(seckill);
    }

    @Test
    public void queryAll() {
        List<Seckill> seckills = seckillDao.queryAll(0, 10);
        System.out.print(seckills);
    }
}