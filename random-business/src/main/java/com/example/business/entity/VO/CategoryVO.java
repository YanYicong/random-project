package com.example.business.entity.VO;

import com.example.business.entity.ChooseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel("随机项组出参")
public class CategoryVO{

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("随机项组名")
    private String categoryName;

    @ApiModelProperty("归属用户")
    private String byUser;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("是否应用")
    private Integer isApply;

    @ApiModelProperty("随机项")
    private List<ChooseEntity> option;
}
