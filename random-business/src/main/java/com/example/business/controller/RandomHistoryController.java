package com.example.business.controller;

import com.alibaba.excel.EasyExcel;
import com.example.business.constants.UtilsConstants;
import com.example.business.entity.DTO.HistoryDTO;
import com.example.business.entity.HistoryEntity;
import com.example.business.entity.VO.HistoryOptionVO;
import com.example.business.entity.VO.HistoryVO;
import com.example.business.exception.ParamValidateException;
import com.example.business.handler.CustomCellWriteHandler;
import com.example.business.handler.ExcelMergeHandler;
import com.example.business.mapper.ExecutionHistoryOptionMapper;
import com.example.business.service.RandomHistoryService;
import com.example.business.utils.Result;
import com.example.business.utils.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
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

    @Resource
    public ExecutionHistoryOptionMapper executionHistoryOptionMapper;

    /**
     * 分页查询历史记录
     *  传参携带分页参数eg：pageNum=1&pageSize=10
     * @return
     */
    @ApiOperation(value = "分页查询历史记录", response = HistoryVO.class)
    @PostMapping("/historyPage")
    public TableDataInfo getAllHistoryPage(HistoryDTO historyDTO) throws ParamValidateException {
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
     * @param
     * @return
     */
    @ApiModelProperty(value = "清空历史记录")
    @DeleteMapping("/historyCleanAll")
    public Result cleanAllHistories() {
        String byUser = UtilsConstants.ADMIN_USER;
        return historyService.moveHistoryAllByUser(byUser) == UtilsConstants.DATABASE_OPERA_SUCCESS ?
                Result.success(UtilsConstants.RESULT_SUCCESS) : Result.error(UtilsConstants.RESULT_ERROR);
    }

    /**
     * 历史记录导出
     * @param
     * @return
     */
    @ApiModelProperty(value = "历史记录导出")
    @GetMapping("/report")
    public void historyReport(HttpServletResponse response, HistoryDTO historyDTO) throws IOException {
        // 请求头与导出文件名称准备
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("history", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 数据准备
        historyDTO.setByUser(UtilsConstants.ADMIN_USER);
        List<HistoryEntity> result = executionHistoryOptionMapper.getHistoryAndOption(historyDTO);

        // 获取模板输入流
        InputStream templateInputStream = this.getClass().getResourceAsStream("/template/configTemplate.xlsx");

        // 需要合并的列
        int[] mergeColumnIndex = {0, 1, 2, 3};
        // 需要从第几行开始合并
        int mergeRowIndex = 0;

        try (OutputStream out = response.getOutputStream()) {
            EasyExcel.write(out, HistoryEntity.class)
                    .autoCloseStream(Boolean.TRUE)
                    .registerWriteHandler(new ExcelMergeHandler(mergeRowIndex, mergeColumnIndex))
                    .registerWriteHandler(new CustomCellWriteHandler())
                    .sheet("history")
                    .doWrite(result);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("导出失败", e);
        }
    }
}
