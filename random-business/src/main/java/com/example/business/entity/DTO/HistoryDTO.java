package com.example.business.entity.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@ApiModel("随机历史入参")
public class HistoryDTO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("选项组名")
    private String randomCategory;

    @ApiModelProperty("运行结果")
    private String runResult;

    @ApiModelProperty("归属用户")
    private String byUser;

    @ApiModelProperty("是否导出")
    private Integer isExport;

    @ApiModelProperty("执行时间")
    private LocalDateTime runTime;

}
