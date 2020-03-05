package com.lomoye.easy.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 2019/8/28 14:56
 * yechangjun
 * 列表模式
 */
@Data
public class ListPatternModel {
    @ApiModelProperty(notes = "爬虫名", required = true)
    private String name;

    @ApiModelProperty(notes = "列表页正则表达式", required = true)
    private String listRegex;

    @ApiModelProperty(notes = "入口页", required = true)
    private String entryUrl;

    @ApiModelProperty(notes = "存储的表名", required = true)
    private String tableName;

    @ApiModelProperty(notes = "字段规则json字符串")
    private String fieldsJson;
}
