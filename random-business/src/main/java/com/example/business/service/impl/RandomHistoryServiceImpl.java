package com.example.business.service.impl;

import com.example.business.constants.UtilsConstants;
import com.example.business.entity.DTO.HistoryDTO;
import com.example.business.entity.VO.HistoryOptionVO;
import com.example.business.entity.VO.HistoryVO;
import com.example.business.mapper.ExecutionHistoryMapper;
import com.example.business.mapper.ExecutionHistoryOptionMapper;
import com.example.business.service.RandomHistoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 随机记录业务实现
 */
@Log4j2
@Service
public class RandomHistoryServiceImpl implements RandomHistoryService {

    @Resource
    private ExecutionHistoryMapper executionHistoryMapper;

    @Resource
    private ExecutionHistoryOptionMapper executionHistoryOptionMapper;

    /**
     * 查询历史信息
     * @param historyDTO
     * @return
     */
    @Override
    public List<HistoryVO> getHistory(HistoryDTO historyDTO) {
        return executionHistoryMapper.findHistoryByAll(historyDTO);
    }

    /**
     * 查询历史信息详情
     * @param historyId
     * @return
     */
    @Override
    public List<HistoryOptionVO> getHistoryOption(String historyId) {
        return executionHistoryOptionMapper.findHistoryByHistoryId(historyId);
    }

    /**
     * 批量删除历史记录
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public int moveHistoryByIds(List<String> ids) {
        int historyRecord = executionHistoryMapper.deleteHistoryByIds(ids);
        log.info("成功删除历史记录{}条", historyRecord);
        int historyOptionRecord = executionHistoryOptionMapper.deleteHistoryOptionByHistoryIds(ids);
        log.info("成功删除历史记录详情{}条", historyOptionRecord);
        return UtilsConstants.DATABASE_OPERA_SUCCESS;
    }

    @Override
    @Transactional
    public int moveHistoryAllByUser(String byUser) {
        int historyRecord = executionHistoryMapper.deleteHistoryByUser(byUser);
        log.info("成功清除历史记录共{}条", historyRecord);
//        获取historyId
        HistoryDTO historyDTO = HistoryDTO.builder()
                .byUser(byUser).build();
        List<HistoryVO> histories = executionHistoryMapper.findHistoryByAll(historyDTO);
//        批量过滤出ids
        List<String> ids = histories.stream()
                .map(HistoryVO :: getId)
                .collect(Collectors.toList());
        int historyOptionRecord = executionHistoryOptionMapper.deleteHistoryOptionByHistoryIds(ids);
        log.info("成功清除历史记录详情共{}条", historyOptionRecord);
        return UtilsConstants.DATABASE_OPERA_SUCCESS;
    }
}
