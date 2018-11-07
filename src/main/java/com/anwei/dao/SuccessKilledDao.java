package com.anwei.dao;

import com.anwei.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

public interface SuccessKilledDao {
    /**
     * 保存记录，可过滤重复
     * @param seckillId
     * @param userPhone
     * @return 插入的行数，>0表示更新成功
     */
    int saveSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * 查询秒杀记录并携带产品信息
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

}
