package com.example.business.mapper;

import com.example.business.entity.DTO.CategoryDTO;
import com.example.business.entity.VO.CategoryVO;
import com.example.business.entity.VO.HistoryOptionVO;
import com.example.business.entity.VO.HistoryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 随机项组表
 */
@Mapper
public interface RandomCategoryMapper {

    /**
     * 查询所有随机项组
     * @return
     */
    public List<CategoryVO> findAllCategory(CategoryDTO categoryDTO);

    /**
     * 新增随机项组
     * @param categoryDTO
     * @return
     */
    public int insertCategory(CategoryDTO categoryDTO);

    /**
     * 修改随机项组(逻辑删除)
     * @param categoryDTO
     * @return
     */
    public int updateCategory(CategoryDTO categoryDTO);


    /**
     * 根据id删除随机项组（物理）
     * @param id
     * @return
     */
    public int deleteByOptionId(String id);

    /**
     * 定时任务逻辑删除转物理删除
     * @return
     */
    public int deleteCategoryByIsApply();


}
