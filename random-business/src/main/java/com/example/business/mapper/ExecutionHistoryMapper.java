package com.example.business.mapper;

import com.example.business.entity.DTO.HistoryDTO;
import com.example.business.entity.HistoryEntity;
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

    /**
     * 根据id(批量)删除历史记录
     * @param ids
     * @return
     */
    public int deleteHistoryByIds(List<String> ids);

    /**
     * 清空历史记录
     * @param byUser
     * @return
     */
    public int deleteHistoryByUser(String byUser);

    /**
     * 清空所有历史记录
     * @return
     */
    public int deleteHistoryAll();



}
