package com.example.business.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("随即历史出参")
public class HistoryVO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("随机项组id")
    private String randomCategory;

    @ApiModelProperty("归属用户")
    private String byUser;

    @ApiModelProperty("执行结果")
    private String runResult;

    @ApiModelProperty("是否删除")
    private Integer isDelete;

    @ApiModelProperty("是否导出")
    private Integer isExport;

    @ApiModelProperty("运行时间")
    private LocalDateTime runTime;

    @ApiModelProperty("删除时间")
    private LocalDateTime deleteTime;

    @ApiModelProperty("导出时间")
    private LocalDateTime exportTime;
}
