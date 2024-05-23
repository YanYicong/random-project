package com.example.business.entity.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel("随机历史详情入参")
public class HistoryOptionDTO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("历史记录id")
    private String historyId;

    @ApiModelProperty("随机项名")
    private String optionName;

    @ApiModelProperty("随机概率")
    private Integer probability;

}
