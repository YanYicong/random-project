package com.example.business.mapper;

import com.example.business.entity.ChooseEntity;
import com.example.business.entity.DTO.CategoryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 随机项表
 */
@Mapper
public interface RandomCategoryOptionMapper {

    /**
     * 通过外键id（组id）查询随机项组成员
     * @param feignId
     * @return
     */
    List<ChooseEntity> findRandomCategoryOptionByForeignId (String feignId);

    /**
     * 新增随机项
     * @param chooseEntity
     * @return
     */
    public int insertCategoryOption(ChooseEntity chooseEntity);

    /**
     * 修改随机项组
     * @param chooseEntity
     * @return
     */
    public int updateCategoryOption(ChooseEntity chooseEntity);

    /**
     * 根据id删除随机项（物理）
     * @param id
     * @return
     */
    public int deletePhysicByOptionId(String id);

    /**
     * 根据组id逻辑删除随机项
     * @param categoryId
     * @return
     */
    public int updateByCategoryId(String categoryId);

    /**
     * 定时任务逻辑删除转物理删除
     * @return
     */
    public int deleteOptionByIsApply();

}
