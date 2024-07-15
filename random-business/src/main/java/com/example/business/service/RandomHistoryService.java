package com.example.business.service;

import com.example.business.entity.DTO.HistoryDTO;
import com.example.business.entity.HistoryEntity;
import com.example.business.entity.VO.HistoryOptionVO;
import com.example.business.entity.VO.HistoryVO;
import com.example.business.exception.ParamValidateException;

import java.util.List;

/**
 * 随机记录
 */
public interface RandomHistoryService {

    public List<HistoryVO> getHistory(HistoryDTO historyDTO) throws ParamValidateException;

    public List<HistoryOptionVO> getHistoryOption(String historyId);

    public int moveHistoryByIds(List<String> ids);

    public int moveHistoryAllByUser(String byUser);
}
