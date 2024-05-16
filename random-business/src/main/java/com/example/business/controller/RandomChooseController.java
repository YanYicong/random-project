package com.example.business.controller;

import com.example.business.entity.DTO.ChooseDTO;
import com.example.business.entity.VO.CategoryVO;
import com.example.business.entity.VO.ChooseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 随机选择页签
 */

@RestController
@RequestMapping("/choosePage")
public class RandomChooseController {

    /**
     * 根据当前登录用户id获取其创建的选项组
     * @return
     */
    @GetMapping("/categories")
    public List<CategoryVO> getCategories(){
        return null;
    }

    /**
     * 根据选项组id获取选项
     * @param id
     * @return
     */
    @GetMapping("/chooseOption")
    public List<ChooseVO> getChooseOption(String id){
        return null;
    }

    /**
     * 执行随机选择
     * @param chooses
     * @return
     */
    @GetMapping("/startRandom")
    public ChooseVO getRandomResult(@RequestBody List<ChooseDTO> chooses){
        return null;
    }
}
