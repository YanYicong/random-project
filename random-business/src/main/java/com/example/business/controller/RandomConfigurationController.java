package com.example.business.controller;

import com.example.business.constants.UtilsConstants;
import com.example.business.entity.ChooseEntity;
import com.example.business.entity.DTO.CategoryDTO;
import com.example.business.exception.ParamValidateException;
import com.example.business.service.RandomConfigurationService;
import com.example.business.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 随机项配置页签
 */
@Api("随机项配置页签")
@RestController
@RequestMapping("/randomOption")
public class RandomConfigurationController {

    @Resource
    private RandomConfigurationService randomConfigurationService;

    /**
     * 新增/修改/逻辑删除 随机项组
     * @param categoryDTO
     * @return
     */
    @ApiOperation("新增/修改/逻辑删除 随机项组")
    @PostMapping("/randomCategory")
    public Result saveRandomCategory(CategoryDTO categoryDTO) throws ParamValidateException {
        return randomConfigurationService.insertAndUpdateCategory(categoryDTO) == UtilsConstants.DATABASE_OPERA_SUCCESS ?
                Result.success(UtilsConstants.RESULT_SUCCESS) : Result.error(UtilsConstants.RESULT_ERROR);
    }

    /**
     * 新增/修改/逻辑删除 随机项
     * @param chooseEntity
     * @return
     */
    @ApiOperation("新增/修改/逻辑删除 随机项")
    @PostMapping("/randomOption")
    public Result saveRandomOption(ChooseEntity chooseEntity){
        return randomConfigurationService.insertAndUpdateOption(chooseEntity) == UtilsConstants.DATABASE_OPERA_SUCCESS ?
                Result.success(UtilsConstants.RESULT_SUCCESS) : Result.error(UtilsConstants.RESULT_ERROR);
    }

    /**
     * 删除 随机项组（物理）
     * @param id
     * @return
     */
    @ApiOperation("物理删除随机项组")
    @DeleteMapping("/delRandomCategory")
    public Result removePhysicsRandomCategory(String id) {
        return randomConfigurationService.delCategory(id) == UtilsConstants.DATABASE_OPERA_SUCCESS ?
                Result.success(UtilsConstants.RESULT_SUCCESS) : Result.error(UtilsConstants.RESULT_ERROR);
    }

    /**
     * 删除 随机项（物理）
     * @param id
     * @return
     */
    @ApiOperation("物理删除随机项")
    @DeleteMapping("/delRandomOption")
    public Result removePhysicsRandomOption(String id){
        return randomConfigurationService.delCategoryOption(id) == UtilsConstants.DATABASE_OPERA_SUCCESS ?
                Result.success(UtilsConstants.RESULT_SUCCESS) : Result.error(UtilsConstants.RESULT_ERROR);
    }


}
