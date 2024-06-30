package com.example.business.service.impl;

import com.example.business.constants.UtilsConstants;
import com.example.business.entity.ChooseEntity;
import com.example.business.entity.DTO.CategoryDTO;
import com.example.business.entity.VO.CategoryVO;
import com.example.business.exception.ParamValidateException;
import com.example.business.mapper.RandomCategoryMapper;
import com.example.business.mapper.RandomCategoryOptionMapper;
import com.example.business.service.RandomConfigurationService;
import com.example.business.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
//        2、逻辑删除/恢复（携带子项）
        if(StringUtils.isEmpty(categoryDTO.getCategoryName())) {
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
//            2、修改和删除/恢复
            return randomCategoryOptionMapper.updateCategoryOption(chooseEntity);
        }
    }

    @Override
    public int delCategory(String id) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(id);
        List<CategoryVO> categories = randomCategoryMapper.findAllCategory(categoryDTO);
        for (CategoryVO vo : categories) {
            int option = randomCategoryOptionMapper.deletePhysicByInCategory(vo.getId());
            log.info("已删除{}项",option);
        }
        return randomCategoryMapper.deleteByOptionId(id);
    }

    @Override
    public int delCategoryOption(String id) {
        return randomCategoryOptionMapper.deletePhysicByOptionId(id);
    }

    @Override
    public int updateAllDelete() {
        String byUser = UtilsConstants.ADMIN_USER;
        CategoryDTO categoryDTO = new  CategoryDTO();
        categoryDTO.setByUser(byUser);
        List<CategoryVO> categories = randomCategoryMapper.findAllCategory(categoryDTO);
        categories.stream().forEach(c -> {
            int option = randomCategoryOptionMapper.updateRestoreOption(c.getId());
            log.info("id:{}子项已恢复{}条", c.getId(),option);
        });
        return randomCategoryMapper.updateRestoreCategory(byUser);
    }

    @Override
    public int killAllDelete() {
        String byUser = UtilsConstants.ADMIN_USER;
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setByUser(byUser);
        List<CategoryVO> categories = randomCategoryMapper.findAllCategory(categoryDTO);
        for (CategoryVO vo : categories) {
            int option = randomCategoryOptionMapper.deleteOptionById(vo.getId());
            log.info("id:{}子项已删除{}条", vo.getId(),option);
        }
        return randomCategoryMapper.deleteAllByUser(byUser);
    }

}
