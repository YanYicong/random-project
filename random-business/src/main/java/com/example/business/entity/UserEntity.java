package com.example.business.entity;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 用户实体
 */
@Data
@ApiModel("用户实体")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @ApiModelProperty("用户id")
    private String id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("显示名")
    private String name;

    @ApiModelProperty("权限(0超管1管理2用户)")
    private Integer auth;

    @ApiModelProperty("状态(0启用1禁用)")
    private Integer state;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

}
