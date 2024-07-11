package com.example.business.mapper;

import com.example.business.entity.DTO.HistoryDTO;
import com.example.business.entity.DTO.HistoryOptionDTO;
import com.example.business.entity.HistoryEntity;
import com.example.business.entity.VO.HistoryOptionVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 执行记录随机项详情表
 */
@Mapper
public interface ExecutionHistoryOptionMapper {

    /**
     * 根据id查询随机历史详情
     * @param historyId
     * @return
     */
    public List<HistoryOptionVO> findHistoryByHistoryId(String historyId);

    /**
     * 生成历史记录详情
     * @param historyOptionDTO
     * @return
     */
    public int addHistoryOptionByAll(List<HistoryOptionDTO> historyOptionDTO);

    /**
     * 根据历史记录id（批量）删除记录详情
     * @param historyIds
     * @return
     */
    public int deleteHistoryOptionByHistoryIds(List<String> historyIds);

    /**
     * 清空所有历史记录
     * @return
     */
    public int deleteHistoryOptionAll();

    /**
     * 查询所有历史记录和记录详情（导出，非树状结构）
     * @param historyDTO
     * @return
     */
    public List<HistoryEntity> getHistoryAndOption(HistoryDTO historyDTO);
}
