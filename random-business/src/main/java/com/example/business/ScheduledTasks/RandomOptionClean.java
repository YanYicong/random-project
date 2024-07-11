package com.example.business.ScheduledTasks;

import com.example.business.mapper.ExecutionHistoryMapper;
import com.example.business.mapper.ExecutionHistoryOptionMapper;
import com.example.business.mapper.RandomCategoryMapper;
import com.example.business.mapper.RandomCategoryOptionMapper;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Log4j2
@Component
public class RandomOptionClean {

    @Resource
    private RandomCategoryMapper randomCategoryMapper;

    @Resource
    private RandomCategoryOptionMapper randomCategoryOptionMapper;

    @Resource
    private ExecutionHistoryMapper executionHistoryMapper;

    @Resource
    private ExecutionHistoryOptionMapper executionHistoryOptionMapper;

    /**
     * 每月一号凌晨一点执行一次随机配置逻辑转物理删除
     */
    @Scheduled(cron = "0 0 1 1 * ?")
//    五秒执行一次（测试）
//    @Scheduled(fixedRate = 5000)
    @Transactional
    public void randomClean() {
        int category = randomCategoryMapper.deleteCategoryByIsApply();
        log .info("【定时任务】随机项组删除成功,共删除{}条数据", category);
        int categoryOption = randomCategoryOptionMapper.deleteOptionByIsApply();
        log.info("【定时任务】随机项删除成功{}条数据", categoryOption);
        log.info("定时任务【清理逻辑删除配置项】执行成功");
    }

    /**
     * 每月一号执行一次删除历史记录
     */
    @Scheduled(cron = "0 0 1 1 * ?")
//    五秒执行一次（测试）
//    @Scheduled(fixedRate = 5000)
    @Transactional
    public void randomHistoryClean() {
        int history = executionHistoryMapper.deleteHistoryAll();
        log .info("【定时任务】历史记录删除成功,共删除{}条数据", history);
        int historyOption = executionHistoryOptionMapper.deleteHistoryOptionAll();
        log .info("【定时任务】历史记录详情删除成功,共删除{}条数据", historyOption);
        log .info("定时任务【清空历史记录】执行成功");
    }

}
