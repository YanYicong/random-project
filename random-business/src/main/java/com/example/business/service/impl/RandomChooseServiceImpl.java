package com.example.business.service.impl;


import com.example.business.Constants.UtilsConstants;
import com.example.business.entity.ChooseEntity;
import com.example.business.entity.DTO.CategoryDTO;
import com.example.business.entity.VO.CategoryVO;
import com.example.business.mapper.RandomCategoryMapper;
import com.example.business.mapper.RandomCategoryOptionMapper;
import com.example.business.service.RandomChooseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 随机选项页签业务实现
 */
@Service
public class RandomChooseServiceImpl implements RandomChooseService {


    @Resource
    private RandomCategoryMapper randomCategoryMapper;

    @Resource
    private RandomCategoryOptionMapper randomCategoryOptionMapper;

    /**
     * 获取当前用户所有选项组  1.0版本无用户，返回所有
     * @return
     */
    @Override
    public List<CategoryVO> getAllCategories() {
//        1、组装查询条件并获取组信息（当前无用户）
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setIsApply(UtilsConstants.isApplyStatic);
        List<CategoryVO> categories = randomCategoryMapper.findAllCategory(categoryDTO);
//        2、置入随机项详情
        for(CategoryVO category : categories){
            category.setOption(randomCategoryOptionMapper.findRandomCategoryOptionByForeignId(category.getId()));
        }
        return categories;
    }

    /**
     * 执行随机选择
     * @param chooses
     * @return
     */
    @Override
    public ChooseEntity getStartResult(List<ChooseEntity> chooses) {
        return null;
    }
}
