package com.example.business.service.impl;

import com.example.business.entity.ChooseEntity;
import com.example.business.entity.DTO.CategoryDTO;
import com.example.business.mapper.RandomCategoryMapper;
import com.example.business.mapper.RandomCategoryOptionMapper;
import com.example.business.service.RandomConfigurationService;
import com.example.business.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 随机项配置业务实现
 */
@Service
public class RandomConfigurationServiceImpl implements RandomConfigurationService {

    @Resource
    private RandomCategoryMapper randomCategoryMapper;

    @Resource
    private RandomCategoryOptionMapper randomCategoryOptionMapper;

    @Override
    public int insertAndUpdateCategory(CategoryDTO categoryDTO) {
//        1、判断操作类型
        int size = randomCategoryMapper.findAllCategory(categoryDTO).size();
//        2、新增
        if(size == 0){
            categoryDTO.setByUser("admin");
            categoryDTO.setId(StringUtils.getUUID());
            return randomCategoryMapper.insertCategory(categoryDTO);
        }else {
//            3、修改或逻辑删除
            return randomCategoryMapper.updateCategory(categoryDTO);
        }
    }

    @Override
    public int insertAndUpdateOption(ChooseEntity chooseEntity) {
//        1、判断操作类型
        if(StringUtils.isEmpty(chooseEntity.getId())){
//            2、新增
            chooseEntity.setId(StringUtils.getUUID());
            return randomCategoryOptionMapper.insertCategoryOption(chooseEntity);
        }else {
//            3、修改和删除
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
