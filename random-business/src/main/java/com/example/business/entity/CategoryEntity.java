package com.example.business.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 随机项组实体(导出)
 */

@Data
@ApiModel("随机项组实体(导出)")
public class CategoryEntity {

    @ApiModelProperty("随机项组")
    private String categoryName;

    @ApiModelProperty("随机项")
    private String categoryOption;

    @ApiModelProperty("概率")
    private String probability;

}
