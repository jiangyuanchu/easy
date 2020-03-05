package com.lomoye.easy.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lomoye.easy.model.ListPatternModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

/**
 * 2019/9/10 14:58
 * yechangjun
 */
@Data
public class ConfigurableSpider extends CommonDomain {

    @ApiModelProperty(notes = "爬虫名")
    private String name;

    @ApiModelProperty(notes = "存储的表名", required = true)
    private String tableName;

    @ApiModelProperty(notes = "列表页正则表达式", required = true)
    private String listRegex;

    @ApiModelProperty(notes = "入口页", required = true)
    private String entryUrl;

    @ApiModelProperty(notes = "字段规则json字符串")
    private String fieldsJson;

    @TableField(exist = false)
    private LinkedHashMap<String/*字段名*/, String/*xpath*/> fields;

    public static ConfigurableSpider valueOf(ListPatternModel model) {
        ConfigurableSpider spider = new ConfigurableSpider();

        spider.setName(model.getName());
        spider.setTableName(model.getTableName());
        spider.setListRegex(model.getListRegex());
        spider.setEntryUrl(model.getEntryUrl());
        spider.setFieldsJson(model.getFieldsJson());
        spider.setCreateTime(LocalDateTime.now());
        spider.setModifyTime(LocalDateTime.now());

        return spider;
    }
}
