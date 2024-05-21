package com.example.business.service;

import com.example.business.entity.ChooseEntity;
import com.example.business.entity.VO.CategoryVO;

import java.util.List;

public interface RandomChooseService {

    /**
     * 获取当前用户所有选项组
     * @return
     */
    public List<CategoryVO> getAllCategories();

    /**
     * 执行随机选择
     * @param chooses
     * @return
     */
    public ChooseEntity getStartResult(List<ChooseEntity> chooses);

}
