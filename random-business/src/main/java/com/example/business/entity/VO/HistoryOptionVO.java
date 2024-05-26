package com.example.business.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


@Data
@ApiModel("随机历史详情出参")
public class HistoryOptionVO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("历史id")
    private String historyId;

    @ApiModelProperty("历史随机项名")
    private String optionName;

    @ApiModelProperty("随机概率")
    private BigDecimal probability;

}
