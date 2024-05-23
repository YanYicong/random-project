package com.example.business.entity.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("随机项组入参")
public class CategoryDTO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("随机项组名")
    private String categoryName;

    @ApiModelProperty("归属用户")
    private String byUser;

    @ApiModelProperty("是否启用")
    private Integer isApply;

    public CategoryDTO(){

    }

    /**
     * 便于获取根据id查询组条件
     * @param categoryId
     */
    public CategoryDTO(String categoryId){
        this.id = categoryId;
    }

}
