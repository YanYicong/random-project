package com.example.business.mapper;

import com.example.business.entity.ChooseEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 随机项表
 */
@Mapper
public interface RandomCategoryOptionMapper {

    /**
     * 通过外键id（组id）查询随机项组成员
     * @param chooseEntity
     * @return
     */
    List<ChooseEntity> findRandomCategoryOptionByForeignId (ChooseEntity chooseEntity);

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
     * 根据归属组删除随即项
     * @param catggoryIn
     * @return
     */
    public int deletePhysicByInCategory(String catggoryIn);

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

    /**
     * 根据id恢复已逻辑删除
     * @return
     */
    public int updateRestoreOption(String id);

    /**
     * 根据id删除项
     * @param id
     * @return
     */
    public int deleteOptionById(String id);

}
