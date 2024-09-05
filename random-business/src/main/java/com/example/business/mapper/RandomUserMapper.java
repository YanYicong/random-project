package com.example.business.mapper;

import com.example.business.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 用户表
 */
@Mapper
public interface RandomUserMapper {

    /**
     * 根据条件查询用户信息
     * @param userEntity
     * @return
     */
    public UserEntity queryUserDetailByAll(UserEntity userEntity);

    /**
     * 添加用户信息
     * @param userEntity
     * @return
     */
    public int addUserDetail(UserEntity userEntity);

    /**
     * 修改用户信息
     * @param userEntity
     * @return
     */
    public int updateUserDetail(UserEntity userEntity);

    public int queryUserDetailNum(UserEntity userEntity);

}
