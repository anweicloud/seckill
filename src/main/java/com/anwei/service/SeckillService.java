package com.anwei.service;

import com.anwei.dto.Exposer;
import com.anwei.dto.SeckillExecution;
import com.anwei.entity.Seckill;
import com.anwei.exception.RepeatKillException;
import com.anwei.exception.SeckillCloseException;
import com.anwei.exception.SeckillException;

import java.util.List;

/**
 * 业务接口：使用者角度设计
 * 1 方法定义粒度
 * 2 参数
 * 3 返回类型
 */
public interface SeckillService {
    /**
     * 秒杀列表
     *
     * @return
     */
    List<Seckill> seckillList();

    /**
     * 秒杀详情
     *
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 导出秒杀地址
     *
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀，通过md5验证合法性
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws SeckillCloseException
     * @throws RepeatKillException
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillCloseException, RepeatKillException, SeckillException;
}
