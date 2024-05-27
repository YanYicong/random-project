package com.example.business.service;

import com.example.business.entity.ChooseEntity;
import com.example.business.entity.DTO.CategoryDTO;
import com.example.business.exception.ParamValidateException;

/**
 * 随机项配置
 */
public interface RandomConfigurationService {

    /**
     * 新增或修改随机项组（逻辑删除）
     * @param categoryDTO
     * @return
     */
    public int insertAndUpdateCategory(CategoryDTO categoryDTO) throws ParamValidateException;

    /**
     * 新增或修改随机项(逻辑删除)
     * @param chooseEntity
     * @return
     */
    public int insertAndUpdateOption(ChooseEntity chooseEntity);


    /**
     * 删除随机项组（物理）
     * @param id
     * @return
     */
    public int delCategory(String id);

    /**
     * 删除随机项（物理）
     * @param id
     * @return
     */
    public int delCategoryOption(String id);
}
