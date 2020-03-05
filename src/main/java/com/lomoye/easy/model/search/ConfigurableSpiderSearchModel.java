package com.lomoye.easy.model.search;

import com.lomoye.easy.model.common.PagedModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 2019/9/1 19:59
 * yechangjun
 */
@Data
public class ConfigurableSpiderSearchModel extends PagedModel {
    @ApiModelProperty(notes = "爬虫名")
    private String name;

    @ApiModelProperty(notes = "存储的表名", required = true)
    private String tableName;
}
