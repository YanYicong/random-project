package com.example.business.mapper;

import com.example.business.entity.DTO.CategoryDTO;
import com.example.business.entity.VO.CategoryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 随机项组表
 */
@Mapper
public interface RandomCategoryMapper {

    /**
     * 查询所有
     * @return
     */
    public List<CategoryVO> findAllCategory(CategoryDTO categoryDTO);

}
