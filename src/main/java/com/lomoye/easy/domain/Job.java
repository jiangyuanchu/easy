package com.lomoye.easy.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lomoye.easy.constants.JobStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 2019/8/28 19:16
 * yechangjun
 */
@Data
public class Job extends CommonDomain {

    @ApiModelProperty(notes = "爬虫id")
    private String spiderId;

    @ApiModelProperty(notes = "爬虫名称")
    private String spiderName;

    @ApiModelProperty(notes = "爬虫表名")
    private String spiderTableName;

    @ApiModelProperty(notes = "状态")
    private String status;

    @ApiModelProperty(notes = "开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(notes = "完成时间")
    private LocalDateTime endTime;

    @ApiModelProperty(notes = "爬取时间(单位秒)")
    private Long timeCost;

    @TableField(exist = false)
    @ApiModelProperty(notes = "爬取数量")
    private Long successNum;

    public static Job valueOf(ConfigurableSpider spider) {
        Job instance = new Job();
        instance.setSpiderId(spider.getId());
        instance.setSpiderName(spider.getName());
        instance.setSpiderTableName(spider.getTableName());
        instance.setStartTime(null);
        instance.setEndTime(null);
        instance.setStatus(JobStatus.WAIT);
        instance.setCreateTime(LocalDateTime.now());
        instance.setModifyTime(LocalDateTime.now());

        return instance;
    }
}
