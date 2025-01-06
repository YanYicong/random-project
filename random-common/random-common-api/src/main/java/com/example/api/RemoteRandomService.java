package com.example.api;

import com.example.api.factory.RandomServiceFallbackFactory;
import com.example.utils.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;

/**
 * 清除回收站和历史记录数据
 */
@FeignClient(contextId = "randomBusinessService" , value = "business" + "/dataClean", fallbackFactory = RandomServiceFallbackFactory.class)
public interface RemoteRandomService {

    @DeleteMapping("/cleanRandomOption")
    Result cleanRandomOption();

    @DeleteMapping("/cleanRandom")
    Result cleanRandom();

    @DeleteMapping("/cleanHistory")
    Result cleanHistory();

    @DeleteMapping("/cleanHistoryOption")
    Result cleanHistoryOption();
}
