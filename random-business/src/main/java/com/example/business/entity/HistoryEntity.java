package com.example.business.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


/**
 * 历史记录实体（导出）
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ColumnWidth(25) // 设置列宽
@HeadRowHeight(36) // 设置表头行高
@ApiModel("历史记录实体(导出)")
public class HistoryEntity {

    @ApiModelProperty("id")
    @ExcelProperty("id")
    @ColumnWidth(0)
    private String id;

    @ApiModelProperty("随机项组名")
    @ExcelProperty("随机项组")
    @ColumnWidth(25)
    private String randomCategory;

    @ApiModelProperty("执行结果")
    @ExcelProperty("执行结果")
    @ColumnWidth(25)
    private String runResult;

    @ApiModelProperty("运行时间")
    @ExcelProperty("执行时间")
    @ColumnWidth(25)
    private String runTime;

    @ApiModelProperty("历史随机项名")
    @ExcelProperty("随机项")
    @ColumnWidth(25)
    private String optionName;

    @ApiModelProperty("随机概率")
    @ExcelProperty("概率")
    @ColumnWidth(25)
    private String probability;

}
