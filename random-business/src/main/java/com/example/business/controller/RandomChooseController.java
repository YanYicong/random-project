package com.example.business.controller;

import com.example.business.entity.ChooseEntity;
import com.example.business.entity.VO.CategoryVO;
import com.example.business.service.RandomChooseService;
import com.example.business.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @param chooses
     * @return
     */
    @GetMapping("/startRandom")
    public ChooseEntity getRandomResult(@RequestBody List<ChooseEntity> chooses){
        return null;
    }
}
