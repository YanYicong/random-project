package com.example.business.controller;

import com.example.business.entity.ChooseEntity;
import com.example.business.entity.DTO.CategoryDTO;
import com.example.business.entity.VO.CategoryVO;
import com.example.business.exception.ProportionException;
import com.example.business.service.RandomChooseService;
import com.example.business.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;

/**
 * 随机选择页签
 */

@Api("随机选择页签")
@RestController
@RequestMapping("/choosePage")
public class RandomChooseController {

    @Resource
    private RandomChooseService randomChooseService;

    /**
     * 根据条件获取随机项组和选项(1.0无用户)
     */
    @ApiOperation(value = "获取随机项组及其随机项", response = CategoryVO.class)
    @GetMapping("/categories")
    public Result getCategories(CategoryDTO categoryDTO){
        return Result.success(randomChooseService.getAllCategories(categoryDTO));
    }


    /**
     * 执行随机选择
     */
    @ApiOperation(value = "执行随机选项", response = ChooseEntity.class)
    @PostMapping("/startRandom")
    public Result getRandomResult(String categoryId) throws ProportionException, NoSuchAlgorithmException {
        return Result.success(randomChooseService.getStartResult(categoryId));
    }
}
