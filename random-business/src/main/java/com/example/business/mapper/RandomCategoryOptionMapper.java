package com.example.business.mapper;

import com.example.business.entity.ChooseEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 随机项表
 */
@Mapper
public interface RandomCategoryOptionMapper {

    ChooseEntity findRandomCategoryOptionByForeignId (String feignId);


}
