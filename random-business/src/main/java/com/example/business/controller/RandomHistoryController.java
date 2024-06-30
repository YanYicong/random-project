package com.example.business.controller;

import com.example.business.constants.UtilsConstants;
import com.example.business.entity.DTO.HistoryDTO;
import com.example.business.entity.VO.HistoryOptionVO;
import com.example.business.entity.VO.HistoryVO;
import com.example.business.service.RandomHistoryService;
import com.example.business.utils.Result;
import com.example.business.utils.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

import static com.example.business.utils.page.PageUtil.startPage;

/**
 * 执行记录页签
 */
@Api("执行记录页签")
@RestController
@RequestMapping("/randomHistory")
public class RandomHistoryController {

    @Resource
    public RandomHistoryService historyService;

    /**
     * 分页查询历史记录
     *  传参携带分页参数eg：pageNum=1&pageSize=10
     * @return
     */
    @ApiOperation(value = "分页查询历史记录", response = HistoryVO.class)
    @PostMapping("/historyPage")
    public TableDataInfo getAllHistoryPage(HistoryDTO historyDTO)
    {
        startPage();
        return Result.successPageInfo(historyService.getHistory(historyDTO));
    }

    /**
     * 获取历史记录详情
     * @param historyId
     * @return
     */
    @ApiOperation(value = "获取历史记录详情", response = HistoryOptionVO.class)
    @GetMapping("/historyOption/{historyId}")
    public Result getHistoryOption(@PathVariable String historyId){
        return Result.success(historyService.getHistoryOption(historyId));
    }

    /**
     * 批量删除历史记录
     * @param ids
     * @return
     */
    @ApiModelProperty(value = "批量删除历史记录")
    @DeleteMapping("/historyClean")
    public Result removeHistories(@RequestBody List<String> ids) {
        return historyService.moveHistoryByIds(ids) == UtilsConstants.DATABASE_OPERA_SUCCESS ?
                Result.success(UtilsConstants.RESULT_SUCCESS) : Result.error(UtilsConstants.RESULT_ERROR);
    }

    /**
     * 清空历史记录
     * @param byUser
     * @return
     */
    @ApiModelProperty(value = "清空历史记录")
    @DeleteMapping("/historyCleanAll")
    public Result cleanAllHistories(String byUser) {
        byUser = UtilsConstants.ADMIN_USER;
        return historyService.moveHistoryAllByUser(byUser) == UtilsConstants.DATABASE_OPERA_SUCCESS ?
                Result.success(UtilsConstants.RESULT_SUCCESS) : Result.error(UtilsConstants.RESULT_ERROR);
    }

}
