package com.example.business.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("随机项实体")
public class ChooseEntity {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("随机项名")
    private String optionName;

    @ApiModelProperty("随机概率")
    private Integer probabilityProportion;

    @ApiModelProperty("随机项组id")
    private String inCategory;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("是否启用")
    private Integer isApply;

}
