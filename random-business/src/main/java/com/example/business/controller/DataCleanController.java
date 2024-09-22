package com.example.business.controller;

import com.example.business.mapper.ExecutionHistoryMapper;
import com.example.business.mapper.ExecutionHistoryOptionMapper;
import com.example.business.mapper.RandomCategoryMapper;
import com.example.business.mapper.RandomCategoryOptionMapper;
import com.example.business.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 数据清除
 */
@Api("数据清除")
@RestController
@RequestMapping("/dataClean")
public class DataCleanController {

    @Resource
    private RandomCategoryMapper randomCategoryMapper;

    @Resource
    private RandomCategoryOptionMapper randomCategoryOptionMapper;

    @Resource
    private ExecutionHistoryMapper executionHistoryMapper;

    @Resource
    private ExecutionHistoryOptionMapper executionHistoryOptionMapper;

    /**
     * 清除回收站中的随机项
     * @return
     */
    @ApiOperation(value = "清除回收站中的随机项")
    @DeleteMapping("/cleanRandomOption")
    public Result cleanRandomOption(){
        return Result.success(randomCategoryOptionMapper.deleteOptionByIsApply());
    }

    /**
     * 清除回收站中的组
     * @return
     */
    @ApiOperation(value = "清除回收站中的组")
    @DeleteMapping("/cleanRandom")
    public Result cleanRandom(){
        return Result.success(randomCategoryMapper.deleteCategoryByIsApply());
    }

    /**
     * 清除历史记录
     * @return
     */
    @ApiOperation(value = "清除历史记录")
    @DeleteMapping("/cleanHistory")
    public Result cleanHistory(){
        return Result.success(executionHistoryMapper.deleteHistoryAll());
    }

    /**
     * 清除历史记录详情
     * @return
     */
    @ApiOperation(value = "清除历史记录详情")
    @DeleteMapping("/cleanHistoryOption")
    public Result clean(){
        return Result.success(executionHistoryOptionMapper.deleteHistoryOptionAll());
    }

}
