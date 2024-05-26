package com.example.business.ScheduledTasks;

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

    /**
     * 每周一凌晨一点执行一次永久删除
     */
    @Scheduled(cron = "0 0 1 ? * 2")
//    五秒执行一次
//    @Scheduled(fixedRate = 5000)
    @Transactional
    public void randomClean() {
        int category = randomCategoryMapper.deleteCategoryByIsApply();
        log .info("随机项组删除成功,共删除{}条数据", category);
        int categoryOption = randomCategoryOptionMapper.deleteOptionByIsApply();
        log.info("随机项删除成功{}条数据", categoryOption);
        log.info("定时任务执行成功");
    }

}
