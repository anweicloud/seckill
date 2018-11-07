package com.anwei.dao;

import com.anwei.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SeckillDao {
    /**
     *
     * @param seckillId
     * @param killTime
     * @return 插入行数，>0表示更新成功
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 秒杀明细
     * @param seckillId 一个参数时不需要加@Param
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 秒杀列表
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
}
