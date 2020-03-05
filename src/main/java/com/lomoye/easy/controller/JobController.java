package com.lomoye.easy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Preconditions;
import com.jfinal.plugin.activerecord.Db;
import com.lomoye.easy.domain.ConfigurableSpider;
import com.lomoye.easy.domain.Job;
import com.lomoye.easy.exception.BusinessException;
import com.lomoye.easy.exception.ErrorCode;
import com.lomoye.easy.model.JobModel;
import com.lomoye.easy.model.common.ResultData;
import com.lomoye.easy.model.common.ResultPagedList;
import com.lomoye.easy.model.search.JobSearchModel;
import com.lomoye.easy.service.ConfigurableSpiderService;
import com.lomoye.easy.service.JobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 2019/9/1 19:22
 * yechangjun
 */
@RestController
@RequestMapping("/job")
@Api(tags = "爬虫任务", description = "爬虫任务 lomoye")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private ConfigurableSpiderService configurableSpiderService;

    @PostMapping
    @ApiOperation("增加任务")
    @ResponseBody
    public ResultData<Job> addJob(@RequestBody JobModel jobModel) {
        Preconditions.checkArgument(jobModel != null);
        if (jobModel.getConfigurableSpiderId() == null) {
            throw new BusinessException(ErrorCode.PARAMETER_ILLEGAL, "蜘蛛id不能为空");
        }

        ConfigurableSpider spider = configurableSpiderService.getById(jobModel.getConfigurableSpiderId());
        if (spider == null) {
            throw new BusinessException(ErrorCode.PARAMETER_ILLEGAL, "蜘蛛不存在");
        }

        Job job = Job.valueOf(spider);
        jobService.save(job);
        return new ResultData<>(job);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除任务")
    @ResponseBody
    public ResultData<Long> deteteJob(@PathVariable Long id) {
        Preconditions.checkArgument(id != null);
        jobService.getBaseMapper().deleteById(id);
        return new ResultData<>(id);
    }

    @PutMapping
    @ApiOperation("修改任务")
    @ResponseBody
    public ResultData<Job> updateJob(@RequestBody Job job) {
        Preconditions.checkArgument(job != null && job.getId() != null);
        job.setModifyTime(LocalDateTime.now());
        jobService.updateById(job);
        return new ResultData<>(job);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询单个任务")
    @ResponseBody
    public ResultData<Job> getJob(@PathVariable Long id) {
        Preconditions.checkArgument(id != null);

        return new ResultData<>(jobService.getById(id));
    }

    @PostMapping("/search")
    @ApiOperation("分页查询")
    @ResponseBody
    public ResultPagedList<Job> searchJob(@RequestBody JobSearchModel searchModel) {
        IPage<Job> page = new Page<>(searchModel.getPageNo(), searchModel.getPageSize());
        QueryWrapper<Job> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(Strings.isNotEmpty(searchModel.getSpiderName()), Job::getSpiderName, searchModel.getSpiderName())
                .like(Strings.isNotEmpty(searchModel.getSpiderTableName()), Job::getSpiderTableName, searchModel.getSpiderTableName())
                .orderByDesc(Job::getCreateTime);

        page = jobService.page(page, queryWrapper);
        List<Job> jobs = page.getRecords();
        if (CollectionUtils.isNotEmpty(jobs)) {
            for (Job job : jobs) {
                Long count = Db.queryLong("select count(*) from " + job.getSpiderTableName() + " where job_uuid=?", job.getId());
                job.setSuccessNum(count == null ? 0L : count);
            }
        }
        return new ResultPagedList<>(page.getRecords(), page.getTotal(), searchModel);
    }
}