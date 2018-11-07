
/*
  竞争

    行级锁

    秒杀高并发，

    1，秒杀接口
    2，执行秒杀
    3，相关查询

    Dao
    Service   声明式事务
    Web
 */
-- 创建数据库
create database seckill;
use seckill;
-- 秒杀库存表
create table seckill (
  seckill_id bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  name VARCHAR(120) NOT NULL COMMENT '商品名称',
  number int NOT NULL COMMENT '库存数量',
  create_time TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
  start_time TIMESTAMP NOT NULL COMMENT '开启时间',
  end_time TIMESTAMP NOT NULL COMMENT '结束时间',
  PRIMARY KEY (seckill_id),
  key idx_start_time(start_time),
  key idx_end_time(end_time),
  key idx_create_time(create_time)
) engine=InnoDB auto_increment=100 DEFAULT CHARSET=utf8 comment='秒杀库存表';

-- 初始化数据
insert into seckill(name, number, start_time, end_time)
  values
    ('1000元秒杀iPhone6s', 100, '2018-11-11 00:00:00', '2018-11-12 00:00:00'),
    ('500元秒杀iPad2', 200, '2018-11-11 00:00:00', '2018-11-12 00:00:00'),
    ('300元秒杀小米4', 300, '2018-11-11 00:00:00', '2018-11-12 00:00:00'),
    ('200元秒杀红米Note', 400, '2018-11-11 00:00:00', '2018-11-12 00:00:00');

-- 秒杀明细表
-- 用户登录认证
create table success_killed (
  seckill_id BIGINT NOT NULL COMMENT '秒杀商品id',
  user_phone BIGINT NOT NULL COMMENT '用户手机号',
  state TINYINT NOT NULL DEFAULT -1 COMMENT '状态-1：无效 0：成功，1：已付款',
  create_time TIMESTAMP NOT NULL COMMENT '创建时间',
  PRIMARY KEY (seckill_id, user_phone), /*联合主键*/
  key idx_create_time(create_time)
) engine=InnoDB DEFAULT CHARSET=utf8 comment='秒杀明细表';