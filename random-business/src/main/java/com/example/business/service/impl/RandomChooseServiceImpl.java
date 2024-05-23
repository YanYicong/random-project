package com.example.business.service.impl;


import com.example.business.Constants.UtilsConstants;
import com.example.business.entity.ChooseEntity;
import com.example.business.entity.DTO.CategoryDTO;
import com.example.business.entity.DTO.HistoryDTO;
import com.example.business.entity.DTO.HistoryOptionDTO;
import com.example.business.entity.VO.CategoryVO;
import com.example.business.mapper.ExecutionHistoryMapper;
import com.example.business.mapper.ExecutionHistoryOptionMapper;
import com.example.business.mapper.RandomCategoryMapper;
import com.example.business.mapper.RandomCategoryOptionMapper;
import com.example.business.service.RandomChooseService;
import com.example.business.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 随机选项页签业务实现
 */
@Log4j2
@Service
public class RandomChooseServiceImpl implements RandomChooseService {


    @Resource
    private RandomCategoryMapper randomCategoryMapper;

    @Resource
    private RandomCategoryOptionMapper randomCategoryOptionMapper;

    @Resource
    private ExecutionHistoryMapper executionHistoryMapper;

    @Resource
    private ExecutionHistoryOptionMapper executionHistoryOptionMapper;

    /**
     * 获取当前用户所有选项组  1.0版本无用户，返回所有
     * @return
     */
    @Override
    public List<CategoryVO> getAllCategories() {
//        1、组装查询条件并获取组信息（当前无用户）
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setIsApply(UtilsConstants.isApplyStatic);
        List<CategoryVO> categories = randomCategoryMapper.findAllCategory(categoryDTO);
//        2、置入随机项详情
        for(CategoryVO category : categories){
            category.setOption(randomCategoryOptionMapper.findRandomCategoryOptionByForeignId(category.getId()));
        }
        return categories;
    }

    /**
     * 执行随机选择
     * @param categoryId
     * @return
     */
    @Override
    @Transactional
    public ChooseEntity getStartResult(String categoryId) {
//        1、获取参与随机的数据
        List<ChooseEntity> randomData = randomCategoryOptionMapper.findRandomCategoryOptionByForeignId(categoryId);
        log.info("随机数据获取成功");
//        2、开始随机并获取结果
        ChooseEntity result = new ChooseEntity();
        try {
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            int randomInt = secureRandom.nextInt(randomData.size());
            result = randomData.get(randomInt);
        }catch (NoSuchAlgorithmException e){
            ;
        }
        log.info("随机结果获取成功");
//        3、使用随机结果构建条件并存入历史记录和历史记录详情
        String categoryName = randomCategoryMapper.findAllCategory
                (new CategoryDTO(result.getId())).get(0).getCategoryName();
        log.info("查询随机项组名成功");
        String uuid = StringUtils.getUUID();
        HistoryDTO historyDTO = HistoryDTO.builder()
                .id(uuid)
                .randomCategory(categoryName)
                .byUser("admin")
                .runResult(result.getOptionName())
                .build();
        int flagHistory = executionHistoryMapper.addHistoryByAll(historyDTO);
        if(flagHistory != 1){
            log.info("历史记录添加失败");
        }
        List<HistoryOptionDTO> historyOptions = new ArrayList<>();
        randomData.stream().forEach(chooseEntity -> {
            HistoryOptionDTO historyOptionDTO = HistoryOptionDTO.builder()
                    .id(StringUtils.getUUID())
                    .historyId(uuid)
                    .optionName(chooseEntity.getOptionName())
                    .probability(chooseEntity.getProbabilityProportion())
                    .build();
            historyOptions.add(historyOptionDTO);
        });
        int flagHistoryOption = executionHistoryOptionMapper.addHistoryOptionByAll(historyOptions);
        if (flagHistoryOption != randomData.size()) {
            log.info("历史记录详情添加失败");
        }
        return result;
    }

}
