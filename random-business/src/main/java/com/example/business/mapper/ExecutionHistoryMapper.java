package com.example.business.mapper;

import com.example.business.entity.DTO.HistoryDTO;
import com.example.business.entity.VO.HistoryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 执行记录表
 */
@Mapper
public interface ExecutionHistoryMapper {

    /**
     * 根据条件查询历史记录
     * @param historyDTO
     * @return
     */
    public List<HistoryVO> findHistoryByAll(HistoryDTO historyDTO);

    /**
     * 生成历史记录
     * @param historyDTO  id randomCategory byUser runResult
     * @return
     */
    public int addHistoryByAll(HistoryDTO historyDTO);


}
