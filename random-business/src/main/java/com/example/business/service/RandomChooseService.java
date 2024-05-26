package com.example.business.service;

import com.example.business.entity.ChooseEntity;
import com.example.business.entity.DTO.CategoryDTO;
import com.example.business.entity.VO.CategoryVO;
import com.example.business.exception.ProportionException;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface RandomChooseService {

    /**
     * 获取当前用户所有选项组
     * @return
     */
    public List<CategoryVO> getAllCategories(CategoryDTO categoryDTO);

    /**
     * 执行随机选择
     * @param categoryId
     * @return
     */
    public ChooseEntity getStartResult(String categoryId) throws ProportionException, NoSuchAlgorithmException;

}
