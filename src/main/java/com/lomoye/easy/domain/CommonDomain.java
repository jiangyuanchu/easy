package com.lomoye.easy.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CommonDomain implements Serializable {
    @TableId(type = IdType.UUID)
    @ApiModelProperty(notes = "主键id")
    private String id;
    @ApiModelProperty(notes = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(notes = "更新时间")
    private LocalDateTime modifyTime;

}