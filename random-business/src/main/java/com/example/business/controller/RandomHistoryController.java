package com.example.business.controller;

import com.example.business.entity.DTO.HistoryDTO;
import com.example.business.service.RandomHistoryService;
import com.example.business.utils.Result;
import com.example.business.utils.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    @ApiOperation(value = "分页查询历史记录", response = HistoryDTO.class)
    @PostMapping("/historyPage")
    public TableDataInfo getAllHistoryPage(HistoryDTO historyDTO)
    {
        startPage();
        return Result.successPageInfo(historyService.getHistory(historyDTO));
    }

}
