package com.example.business.service;

import com.example.business.entity.DTO.ChooseDTO;
import com.example.business.entity.VO.CategoryVO;
import com.example.business.entity.VO.ChooseVO;

import java.util.List;

public interface RandomChooseService {

    /**
     * 获取当前用户所有选项组
     * @return
     */
    public List<CategoryVO> getAllCategories();

    /**
     * 根据选项组id获取选项
     * @param id
     * @return
     */
    public List<ChooseVO> getChooseOptionById(String id);

    /**
     * 执行随机选择
     * @param chooses
     * @return
     */
    public ChooseVO getStartResult(List<ChooseDTO> chooses);

}
