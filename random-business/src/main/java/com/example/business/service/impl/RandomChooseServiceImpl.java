package com.example.business.service.impl;


import com.example.business.constants.ExceptionInfoConstants;
import com.example.business.constants.UtilsConstants;
import com.example.business.entity.ChooseEntity;
import com.example.business.entity.DTO.CategoryDTO;
import com.example.business.entity.DTO.HistoryDTO;
import com.example.business.entity.DTO.HistoryOptionDTO;
import com.example.business.entity.VO.CategoryVO;
import com.example.business.exception.ParamValidateException;
import com.example.business.exception.RandomSystemException;
import com.example.business.exception.ResultNullDataException;
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
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

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
    public List<CategoryVO> getAllCategories(CategoryDTO categoryDTO) {
//        1、组装查询条件（当前无用户）
//        (如果需要查询已删除数据则需要查询所有数据然后过滤掉随机项和组都未删除的数据以显示所有删除的项和组)
        categoryDTO.setByUser(UtilsConstants.ADMIN_USER);
        boolean flag = false;
        ChooseEntity chooseEntity = new ChooseEntity();
        if(categoryDTO.getIsApply() == UtilsConstants.notApplyStatic){
            categoryDTO.setIsApply(null);
            chooseEntity.setIsApply(UtilsConstants.notApplyStatic);
            flag = true;
        }else {
            chooseEntity.setIsApply(categoryDTO.getIsApply());
        }
//        2、根据条件查询组和项数据
        List<CategoryVO> categories = randomCategoryMapper.findAllCategory(categoryDTO);
        for(CategoryVO category : categories){
            chooseEntity.setInCategory(category.getId());
            category.setOption(randomCategoryOptionMapper.findRandomCategoryOptionByForeignId(chooseEntity));
        }
//        2.1、查找的是已删除数据，则需要过滤
        List<CategoryVO> result = new ArrayList<>();
        if(flag){
            for (CategoryVO vo : categories) {
                if(vo.getOption().toString().contains("isApply=1")
                        || vo.getIsApply() == UtilsConstants.notApplyStatic){
                    result.add(vo);
                }
            }
            categories = result;
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
    public ChooseEntity getStartResult(String categoryId) throws NoSuchAlgorithmException, ResultNullDataException, RandomSystemException, ParamValidateException {
//        1、获取参与随机的数据
        ChooseEntity chooseParam = new ChooseEntity();
        chooseParam.setIsApply(UtilsConstants.isApplyStatic);
        chooseParam.setInCategory(categoryId);
        List<ChooseEntity> randomData = randomCategoryOptionMapper.findRandomCategoryOptionByForeignId(chooseParam);
        if(randomData.size() == UtilsConstants.DATA_ZERO_SIZE){
            throw new ResultNullDataException();
        }
        log.info("随机数据获取成功");
//        2、数据校验，校验比例正确性
        BigDecimal sumProportion = BigDecimal.ZERO;
        boolean flag = false;
        for (ChooseEntity choose : randomData) {
            if(StringUtils.isNull(choose.getProbabilityProportion())){
                flag = true;
                continue;
            }
            sumProportion = sumProportion.add(choose.getProbabilityProportion());
        }
        if(!flag && sumProportion.intValue() < UtilsConstants.PROPORTION_FULL){
            log.info("比例值总和不足100，请重新配置！");
            throw new RandomSystemException();
        }
        if (sumProportion.intValue() > UtilsConstants.PROPORTION_FULL) {
            log.info("比例值总和超过100，请重新配置！");
            throw new ParamValidateException(ExceptionInfoConstants.PARAM_PROPORTION_OUT_OF_EXCEPTION);
        }
//        3、开始随机并获取结果
        ChooseEntity result = random(randomData);
        log.info("随机结果获取成功");
//        4、使用随机结果构建条件并存入历史记录和历史记录详情
        String categoryName = randomCategoryMapper.findAllCategory
                (new CategoryDTO(result.getInCategory())).get(UtilsConstants.FIRST_ONE_INDEX).getCategoryName();
        log.info("查询随机项组名成功");
        String uuid = StringUtils.getUUID();
        HistoryDTO historyDTO = HistoryDTO.builder()
                .id(uuid)
                .randomCategory(categoryName)
                .byUser(UtilsConstants.ADMIN_USER)
                .runResult(result.getOptionName())
                .build();
        int flagHistory = executionHistoryMapper.addHistoryByAll(historyDTO);
        if(flagHistory != UtilsConstants.DATABASE_OPERA_SUCCESS){
            log.info("历史记录添加失败");
            throw new RandomSystemException();
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
            throw new RandomSystemException();
        }
        return result;
    }

    /**
     * 执行
     * @param randomData
     * @return
     */
    public ChooseEntity random(List<ChooseEntity> randomData) throws NoSuchAlgorithmException, ParamValidateException {

        final BigDecimal hundred = new BigDecimal("100");

//        判断是否设置过自定义概率
        BigDecimal sum = new BigDecimal("0");
        int count = randomData.size();
        for(ChooseEntity chooseEntity : randomData){
            if(StringUtils.isNotNull(chooseEntity.getProbabilityProportion())){
                sum = sum.add (chooseEntity.getProbabilityProportion());
                count--;
            }
        }
//        如果所有元素都没有配置概率则按照平均概率
        if (BigDecimal.ZERO.compareTo(sum) == 0) {
            int randomInt = 0;
            try {
                SecureRandom secureRandom = SecureRandom.getInstanceStrong();
                randomInt = secureRandom.nextInt(randomData.size());
            }catch (NoSuchAlgorithmException e){
                throw new NoSuchAlgorithmException();
            }
            return randomData.get(randomInt);
        }
//        如果存在设置概率则将其他概率平均分配并补齐
//        判断概率是否合法，是否存在需要手动添加的空概率
        BigDecimal flag = hundred.subtract(sum);
        if (flag.compareTo(BigDecimal.ZERO) < 0) {
            throw new ParamValidateException(ExceptionInfoConstants.PARAM_PROPORTION_EXCEPTION);
        }
        BigDecimal dividend = flag.divide(new BigDecimal(String.valueOf(count)));
        randomData.stream().forEach(chooseEntity -> {
            if (StringUtils.isNull(chooseEntity.getProbabilityProportion())) {
                chooseEntity.setProbabilityProportion(dividend);
            }
        });
//        获取随机概率
        int randomInt = 0;
        try {
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            randomInt = secureRandom.nextInt(10001);
        }catch (NoSuchAlgorithmException e){
            throw new NoSuchAlgorithmException();
        }
        BigDecimal randomIndex = new BigDecimal(String.valueOf(randomInt / 100.0));
//        根据概率分布元素并将随机数定位
        sum = new BigDecimal("0");
        ChooseEntity result = randomData.get(0);
        for(ChooseEntity chooseEntity : randomData){
            sum = sum.add(chooseEntity.getProbabilityProportion());
            if(randomIndex.compareTo(sum) < 0){
                result = chooseEntity;
               break;
            }
            result = chooseEntity;
        }
        return result;
    }

}
