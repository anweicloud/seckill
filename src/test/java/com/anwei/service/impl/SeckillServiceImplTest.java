package com.anwei.service.impl;

import com.anwei.dto.Exposer;
import com.anwei.dto.SeckillExecution;
import com.anwei.entity.Seckill;
import com.anwei.exception.RepeatKillException;
import com.anwei.exception.SeckillCloseException;
import com.anwei.exception.SeckillException;
import com.anwei.service.SeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring.xml", "classpath:spring-mybatis.xml"})
public class SeckillServiceImplTest {

    @Resource
    SeckillService seckillService;

    @Test
    public void seckillList() {
        List<Seckill> seckills = seckillService.seckillList();
        System.out.println(seckills);
    }

    @Test
    public void getById() {
        Seckill seckill = seckillService.getById(100);
        System.out.println(seckill);
    }

    @Test
    public void exportSeckillUrl() {
        Exposer exposer = seckillService.exportSeckillUrl(100);
        System.out.println(exposer);

        if (exposer.isExposed()) {
            try {
                SeckillExecution se = seckillService.executeSeckill(exposer.getSeckillId(), 18286176421L, exposer.getMd5());
            } catch (RepeatKillException e) {
                System.out.println(e.getMessage());
            } catch (SeckillCloseException e) {
                System.out.println(e.getMessage());
            } catch (SeckillException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    @Test
    public void executeSeckill() {
        SeckillExecution se = seckillService.executeSeckill(100, 18286176421L, "694e2f6d89e6f68fe11f1daed6527346");
        System.out.println(se);
    }
}