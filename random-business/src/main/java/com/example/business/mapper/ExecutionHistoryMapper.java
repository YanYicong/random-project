package com.example.business.mapper;

import com.example.business.entity.DTO.HistoryDTO;
import com.example.business.entity.VO.HistoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 执行记录表
 */
@Mapper
public interface ExecutionHistoryMapper {

    public List<HistoryVO> findHistoryById(HistoryDTO historyDTO);

}
