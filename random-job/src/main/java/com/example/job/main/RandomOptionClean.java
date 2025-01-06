package com.example.job.main;

import com.example.api.RemoteRandomService;
import com.example.utils.utils.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Log4j2
@Component
@EnableScheduling
public class RandomOptionClean {

    @Resource
    private RemoteRandomService remoteRandomService;

    /**
     * 每月一号凌晨一点执行一次随机配置逻辑转物理删除
     */
    @Scheduled(cron = "0 0 1 1 * ?")
//    五秒执行一次（测试）
//    @Scheduled(cron = "3/5 * * * * ?")
    @Transactional
    public void randomClean() {
        Result category = remoteRandomService.cleanRandom();
        log .info("【定时任务】随机项组删除成功,共删除{}条数据", category.get("data"));
        Result categoryOption = remoteRandomService.cleanRandomOption();
        log.info("【定时任务】随机项删除成功{}条数据", categoryOption.get("data"));
        log.info("定时任务【清理逻辑删除配置项】执行成功");
    }

    /**
     * 每月一号执行一次删除历史记录
     */
    @Scheduled(cron = "0 0 1 1 * ?")
//    五秒执行一次（测试）
//    @Scheduled(cron = "3/5 * * * * ?")
    @Transactional
    public void randomHistoryClean() {
        Result history = remoteRandomService.cleanHistory();
        log .info("【定时任务】历史记录删除成功,共删除{}条数据", history.get("data"));
        Result historyOption = remoteRandomService.cleanHistoryOption();
        log .info("【定时任务】历史记录详情删除成功,共删除{}条数据", historyOption.get("data"));
        log .info("定时任务【清空历史记录】执行成功");
    }

}
