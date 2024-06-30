package com.example.business.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("随机历史出参")
public class HistoryVO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("随机项组名")
    private String randomCategory;

    @ApiModelProperty("归属用户")
    private String byUser;

    @ApiModelProperty("执行结果")
    private String runResult;

    @ApiModelProperty("是否导出")
    private Integer isExport;

    @ApiModelProperty("运行时间")
    private LocalDateTime runTime;

    @ApiModelProperty("导出时间")
    private LocalDateTime exportTime;
}
