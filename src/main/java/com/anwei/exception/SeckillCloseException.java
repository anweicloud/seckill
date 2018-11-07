package com.anwei.exception;

/**
 * 秒杀关闭
 */
public class SeckillCloseException extends SeckillException {
    public SeckillCloseException() {
    }

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
