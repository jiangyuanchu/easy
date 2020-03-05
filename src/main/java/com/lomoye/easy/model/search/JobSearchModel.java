package com.lomoye.easy.model.search;

import com.lomoye.easy.model.common.PagedModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 2019/9/1 19:59
 * yechangjun
 */
@Data
public class JobSearchModel extends PagedModel {
    @ApiModelProperty(notes = "爬虫名")
    private String spiderName;

    @ApiModelProperty(notes = "爬虫存储的表名", required = true)
    private String spiderTableName;
}
