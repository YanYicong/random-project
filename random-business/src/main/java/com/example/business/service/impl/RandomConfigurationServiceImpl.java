package com.example.business.service.impl;

import com.example.business.constants.UtilsConstants;
import com.example.business.entity.ChooseEntity;
import com.example.business.entity.DTO.CategoryDTO;
import com.example.business.exception.ParamValidateException;
import com.example.business.exception.ProportionException;
import com.example.business.mapper.RandomCategoryMapper;
import com.example.business.mapper.RandomCategoryOptionMapper;
import com.example.business.service.RandomConfigurationService;
import com.example.business.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 随机项配置业务实现
 */
@Log4j2
@Service
public class RandomConfigurationServiceImpl implements RandomConfigurationService {

    @Resource
    private RandomCategoryMapper randomCategoryMapper;

    @Resource
    private RandomCategoryOptionMapper randomCategoryOptionMapper;

    @Override
    @Transactional
    public int insertAndUpdateCategory(CategoryDTO categoryDTO) throws ParamValidateException{
//        1、新增
        if(StringUtils.isEmpty(categoryDTO.getId())){
            categoryDTO.setByUser(UtilsConstants.ADMIN_USER);
            categoryDTO.setId(StringUtils.getUUID());
            return randomCategoryMapper.insertCategory(categoryDTO);
        }
//        2、逻辑删除（携带子项）
        if(categoryDTO.getIsApply() == UtilsConstants.notApplyStatic) {
            int option = randomCategoryOptionMapper.updateByCategoryId(categoryDTO.getId());
            log.info("已删除{}条子项", option);
            return randomCategoryMapper.updateCategory(categoryDTO);
        }
//        3、修改
        return randomCategoryMapper.updateCategory(categoryDTO);
    }

    @Override
    public int insertAndUpdateOption(ChooseEntity chooseEntity) {
//        1、新增
        if(StringUtils.isEmpty(chooseEntity.getId())){
            chooseEntity.setId(StringUtils.getUUID());
            return randomCategoryOptionMapper.insertCategoryOption(chooseEntity);
        }else {
//            2、修改和删除
            return randomCategoryOptionMapper.updateCategoryOption(chooseEntity);
        }
    }

    @Override
    public int delCategory(String id) {
        return randomCategoryMapper.deleteByOptionId(id);
    }

    @Override
    public int delCategoryOption(String id) {
        return randomCategoryOptionMapper.deletePhysicByOptionId(id);
    }

}
