package com.lomoye.easy.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 2019/9/11 16:01
 * yechangjun
 */
@Data
public class JobModel {
    @ApiModelProperty(notes = "爬虫id")
    private String configurableSpiderId;
}
