package com.example.business.service.impl;

import com.example.business.entity.DTO.HistoryDTO;
import com.example.business.entity.VO.HistoryVO;
import com.example.business.mapper.ExecutionHistoryMapper;
import com.example.business.service.RandomHistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 随机记录业务实现
 */
@Service
public class RandomHistoryServiceImpl implements RandomHistoryService {

    @Resource
    private ExecutionHistoryMapper executionHistoryMapper;

    @Override
    public List<HistoryVO> getHistory(HistoryDTO historyDTO) {
//        1、构建条件(暂无管理员端)
        historyDTO.setIsDelete(1);
        return executionHistoryMapper.findHistoryById(historyDTO);
    }
}
