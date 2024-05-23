package com.example.business.controller;

import com.example.business.entity.ChooseEntity;
import com.example.business.entity.VO.CategoryVO;
import com.example.business.service.RandomChooseService;
import com.example.business.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
     * 根据当前登录用户id获取其创建的选项组和选项(1.0无用户)
     * @return
     */
    @ApiOperation(value = "获取选项组及其选项", response = CategoryVO.class)
    @GetMapping("/categories")
    public Result getCategories(){
        return Result.success(randomChooseService.getAllCategories());
    }


    /**
     * 执行随机选择
     * @param categoryId
     * @return
     */
    @ApiOperation(value = "执行随机选项", response = ChooseEntity.class)
    @PostMapping("/startRandom")
    public Result getRandomResult(String categoryId){
        return Result.success(randomChooseService.getStartResult(categoryId));
    }
}
