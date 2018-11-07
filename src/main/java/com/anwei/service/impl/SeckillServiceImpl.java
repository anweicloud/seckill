package com.anwei.service.impl;

import com.anwei.dao.SeckillDao;
import com.anwei.dao.SuccessKilledDao;
import com.anwei.dto.Exposer;
import com.anwei.dto.SeckillExecution;
import com.anwei.entity.Seckill;
import com.anwei.entity.SuccessKilled;
import com.anwei.enums.SeckillStateEnum;
import com.anwei.exception.RepeatKillException;
import com.anwei.exception.SeckillCloseException;
import com.anwei.exception.SeckillException;
import com.anwei.service.SeckillService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService {
    private final String slat = "Adason422!#!#!(AZZ";//混淆MD5

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Override
    public List<Seckill> seckillList() {
        return seckillDao.queryAll(0, 4);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = getById(seckillId);
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }

        long startTime = seckill.getStartTime().getTime(),
                endTime = seckill.getEndTime().getTime(),
                now = new Date().getTime();

        //now = toDate().getTime();

        if (now < startTime || now > endTime) {
            return new Exposer(false, seckillId, now, startTime, endTime);
        }

        String md5 = getMD5(seckillId);//TODO
        return new Exposer(true, md5, seckillId);
    }

    /**
     * 注解事务优点
     *  1. 利于协作
     *  2. 灵活主动
     *  3. 事务控制范围
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillCloseException
     * @throws RepeatKillException
     * @throws SeckillException
     */
    @Transactional
    @Override
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillCloseException, RepeatKillException, SeckillException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }

        Date nowTime = new Date();
        //nowTime = toDate();

        try {
            int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
            if (updateCount <= 0) {
                throw new SeckillCloseException("seckill closed");
            } else {
                int insertCount = successKilledDao.saveSuccessKilled(seckillId, userPhone);
                if (insertCount > 0) {
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                } else {
                    throw new RepeatKillException("seckill repeat");
                }

            }
        } catch (SeckillCloseException e) {
            throw e;
        } catch (RepeatKillException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SeckillException("seckill service error:" + e.getMessage());
        }
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "+" + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    private Date toDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            cal.setTime(sdf.parse("2018-10-01"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal.getTime();
    }
}
