package com.lomoye.easy.controller;

import com.google.common.base.Strings;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.lomoye.easy.domain.Job;
import com.lomoye.easy.exception.BusinessException;
import com.lomoye.easy.exception.ErrorCode;
import com.lomoye.easy.model.common.ResultPagedList;
import com.lomoye.easy.model.search.JobRecordSearchModel;
import com.lomoye.easy.service.JobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 2019/9/1 19:22
 * yechangjun
 */
@RestController
@RequestMapping("/job-record")
@Api(tags = "爬虫任务结果", description = "爬虫任务结果 lomoye")
public class JobRecordController {

    @Autowired
    private JobService jobService;

    @PostMapping("/search")
    @ApiOperation("分页查询")
    @ResponseBody
    public ResultPagedList<Map<String, Object>> searchJobRecord(@RequestBody JobRecordSearchModel searchModel) {
        if (Strings.isNullOrEmpty(searchModel.getJobUuid())) {
            throw new BusinessException(ErrorCode.PARAMETER_ILLEGAL, "jobUuid不能为空");
        }

        Job job = jobService.getById(searchModel.getJobUuid());
        if (job == null) {
            throw new BusinessException(ErrorCode.PARAMETER_ILLEGAL, "任务不存在");
        }

        String select = "SELECT *";
        String sqlExceptSelect = " FROM " + job.getSpiderTableName() + " WHERE job_uuid = ? ORDER BY id DESC" ;
        Page<Record> page = Db.paginate(searchModel.getPageNo().intValue(), searchModel.getPageSize().intValue(), select, sqlExceptSelect, searchModel.getJobUuid());

        List<Record> records = page.getList();
        if (records == null) {
            records = new ArrayList<>();
        }

        List<Map<String, Object>> rds = records.stream().map(Record::getColumns).collect(Collectors.toList());

        return new ResultPagedList<>(rds, (long) page.getTotalRow(), searchModel);
    }
}