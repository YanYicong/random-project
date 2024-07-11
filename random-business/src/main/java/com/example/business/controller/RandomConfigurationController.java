package com.example.business.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.example.business.constants.UtilsConstants;
import com.example.business.entity.CategoryEntity;
import com.example.business.entity.ChooseEntity;
import com.example.business.entity.DTO.CategoryDTO;
import com.example.business.entity.DTO.CategoryOptionDTO;
import com.example.business.exception.ParamValidateException;
import com.example.business.mapper.RandomCategoryMapper;
import com.example.business.mapper.RandomCategoryOptionMapper;
import com.example.business.service.RandomConfigurationService;
import com.example.business.utils.Result;
import com.example.business.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * 随机项配置页签
 */
@Api("随机项配置页签")
@RestController
@RequestMapping("/randomOption")
@Log4j2
public class RandomConfigurationController {

    @javax.annotation.Resource
    private RandomConfigurationService randomConfigurationService;

    @javax.annotation.Resource
    private RandomCategoryMapper randomCategoryMapper;

    @javax.annotation.Resource
    private RandomCategoryOptionMapper randomCategoryOptionMapper;

    /**
     * 新增/修改/逻辑删除 随机项组
     * @param categoryDTO
     * @return
     */
    @ApiOperation("新增/修改/逻辑删除/恢复 随机项组")
    @PostMapping("/randomCategory")
    public Result saveRandomCategory(@RequestBody CategoryDTO categoryDTO) throws ParamValidateException {
        return randomConfigurationService.insertAndUpdateCategory(categoryDTO) == UtilsConstants.DATABASE_OPERA_SUCCESS ?
                Result.success(UtilsConstants.RESULT_SUCCESS) : Result.error(UtilsConstants.RESULT_ERROR);
    }

    /**
     * 新增/修改/逻辑删除/恢复 随机项
     * @param chooseEntity
     * @return
     */
    @ApiOperation("新增/修改/逻辑删除 随机项")
    @PostMapping("/randomOption")
    public Result saveRandomOption(@RequestBody ChooseEntity chooseEntity){
        return randomConfigurationService.insertAndUpdateOption(chooseEntity) == UtilsConstants.DATABASE_OPERA_SUCCESS ?
                Result.success(UtilsConstants.RESULT_SUCCESS) : Result.error(UtilsConstants.RESULT_ERROR);
    }

    /**
     * 删除 随机项组（物理）
     * @param id
     * @return
     */
    @ApiOperation("物理删除随机项组")
    @DeleteMapping("/delRandomCategory")
    public Result removePhysicsRandomCategory(String id) {
        return randomConfigurationService.delCategory(id) == UtilsConstants.DATABASE_OPERA_SUCCESS ?
                Result.success(UtilsConstants.RESULT_SUCCESS) : Result.error(UtilsConstants.RESULT_ERROR);
    }

    /**
     * 删除 随机项（物理）
     * @param id
     * @return
     */
    @ApiOperation("物理删除随机项")
    @DeleteMapping("/delRandomOption")
    public Result removePhysicsRandomOption(String id){
        return randomConfigurationService.delCategoryOption(id) == UtilsConstants.DATABASE_OPERA_SUCCESS ?
                Result.success(UtilsConstants.RESULT_SUCCESS) : Result.error(UtilsConstants.RESULT_ERROR);
    }

    /**
     * 恢复全部
     * @return
     */
    @ApiOperation("恢复全部")
    @PostMapping("/restoreAll")
    public Result restoreAllByUser() {
        return new Result(UtilsConstants.REQUEST_SUCCESS, String.valueOf(randomConfigurationService.updateAllDelete()));
    }

    /**
     * 永久删除全部
     * @return
     */
    @ApiModelProperty("永久删除全部")
    @DeleteMapping("/killAll")
    public Result killAllByUser() {
        return new Result(UtilsConstants.REQUEST_SUCCESS, String.valueOf(randomConfigurationService.killAllDelete()));
    }

    @ApiModelProperty("导入模板下载")
    @GetMapping("/template")
    public ResponseEntity<Resource> downLoadTemplate() {
        Resource resource = new ClassPathResource("template/configTemplate.xlsx");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment","configTemplate.xlsx");
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @ApiModelProperty("配置导入")
    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (!StringUtils.isExcelXlsx(file)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("文件为空或格式不正确");
        }
//        解析数据
        try (InputStream inputStream = file.getInputStream()) {
            List<CategoryEntity> dataList = new ArrayList<>();
            EasyExcel.read(inputStream, CategoryEntity.class, new ReadListener<CategoryEntity>() {
                @Override
                public void invoke(CategoryEntity data, AnalysisContext context) {
                    dataList.add(data);
                }
                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                }
            }).sheet().doRead();
//            组装数据并存储
            List<CategoryDTO> categories = new ArrayList<>();
            List<CategoryOptionDTO> categoryOptions = new ArrayList<>();
            String id = null;
            for (CategoryEntity t : dataList) {
                if(StringUtils.isNotNull(t.getCategoryName())){
                    id = StringUtils.getUUID();
                     categories.add(CategoryDTO.builder()
                             .id(id)
                             .categoryName(t.getCategoryName())
                             .byUser(UtilsConstants.ADMIN_USER)
                             .build());
                }
                 categoryOptions.add(CategoryOptionDTO.builder()
                         .id(StringUtils.getUUID())
                         .optionName(t.getCategoryOption())
                         .inCategory(id)
                         .probabilityProportion(
                                 Optional.ofNullable(t.getProbability())
                                         .filter(p -> !p.isEmpty())
                                         .map(p -> new BigDecimal(p.substring(0, p.length() - 1)))
                                         .orElse(null))
                         .build());
            }
            int sumCategoryCount = randomCategoryMapper.insertCategories(categories);
            int sumCategoryOptionCount = randomCategoryOptionMapper.insertCategoryOptions(categoryOptions);
            log.info("已新增随机项组{}个",sumCategoryCount);
            log.info("已新增随机项{}个",sumCategoryOptionCount);
            return ResponseEntity.ok("文件上传并解析成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件解析失败");
        }
    }
}
