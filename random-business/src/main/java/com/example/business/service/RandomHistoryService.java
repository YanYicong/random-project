package com.example.business.service;

import com.example.business.entity.DTO.HistoryDTO;
import com.example.business.entity.VO.HistoryOptionVO;
import com.example.business.entity.VO.HistoryVO;

import java.util.List;

/**
 * 随机记录
 */
public interface RandomHistoryService {

    public List<HistoryVO> getHistory(HistoryDTO historyDTO);

    public List<HistoryOptionVO> getHistoryOption(String historyId);

}
