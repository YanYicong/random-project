package com.example.business.entity.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@ApiModel("随机项入参")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryOptionDTO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("随机项")
    private String optionName;

    @ApiModelProperty("概率")
    private BigDecimal probabilityProportion;

    @ApiModelProperty("归属组")
    private String inCategory;

    @ApiModelProperty("是否可用")
    private Integer isApply;

}
