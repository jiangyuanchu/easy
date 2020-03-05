package com.lomoye.easy.model.search;

import com.lomoye.easy.model.common.PagedModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 2019/9/1 19:59
 * yechangjun
 */
@Data
public class JobRecordSearchModel extends PagedModel {
    @ApiModelProperty(notes = "任务uuid")
    private String jobUuid;
}
