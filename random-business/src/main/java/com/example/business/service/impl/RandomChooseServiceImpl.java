package com.example.business.service.impl;


import com.example.business.entity.DTO.ChooseDTO;
import com.example.business.entity.VO.CategoryVO;
import com.example.business.entity.VO.ChooseVO;
import com.example.business.service.RandomChooseService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 随机选项页签业务实现
 */
@Service
public class RandomChooseServiceImpl implements RandomChooseService {

    /**
     * 获取当前用户所有选项组
     * @return
     */
    @Override
    public List<CategoryVO> getAllCategories() {
        return Collections.emptyList();
    }

    /**
     * 根据选项组id获取选项
     * @param id
     * @return
     */
    @Override
    public List<ChooseVO> getChooseOptionById(String id) {
        return Collections.emptyList();
    }

    /**
     * 执行随机选择
     * @param chooses
     * @return
     */
    @Override
    public ChooseVO getStartResult(List<ChooseDTO> chooses) {
        return null;
    }
}
