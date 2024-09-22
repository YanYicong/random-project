package com.example.api.factory;

import com.example.api.RemoteRandomService;
import com.example.utils.utils.Result;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * 数据清除降级处理
 */
@Log4j2
public class RandomServiceFallbackFactory implements FallbackFactory<RemoteRandomService> {

    @Override
    public RemoteRandomService create(Throwable cause) {
        return new RemoteRandomService() {

            public Result cleanRandomOption() {
                log.info("服务调用异常：{}", cause.getMessage());
                return Result.error("服务异常");
            }
            public Result cleanRandom() {
                log.info("服务调用异常：{}", cause.getMessage());
                return Result.error("服务异常");
            }
            public Result cleanHistory() {
                log.info("服务调用异常：{}", cause.getMessage());
                return Result.error("服务异常");
            }
            public Result cleanHistoryOption() {
                log.info("服务调用异常：{}", cause.getMessage());
                return Result.error("服务异常");
            }
        };
    }
}
