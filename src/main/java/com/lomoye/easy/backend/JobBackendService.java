package com.lomoye.easy.backend;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lomoye.easy.ListPatternProcessor;
import com.lomoye.easy.constants.JobStatus;
import com.lomoye.easy.domain.ConfigurableSpider;
import com.lomoye.easy.domain.Job;
import com.lomoye.easy.service.ConfigurableSpiderService;
import com.lomoye.easy.service.JobService;
import com.lomoye.easy.utils.LocalDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 2019/9/11 16:19
 * yechangjun
 */
@Slf4j
@Service
public class JobBackendService extends Thread {

    @Autowired
    private JobService jobService;

    @Autowired
    private ConfigurableSpiderService configurableSpiderService;

    @Override
    public void run() {
        while (true) {
            QueryWrapper<Job> queryWrapper = new QueryWrapper<>();
            List<Job> jobs = jobService.list(queryWrapper.lambda().eq(Job::getStatus, JobStatus.WAIT));
            if (CollectionUtils.isEmpty(jobs)) {
                try {
                    log.info("JobBackendService empty job sleep 1s");
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    log.info("job backend sleep error", e);
                }

                continue;
            }
            for (Job job : jobs) {
                runJob(job);
            }
        }

    }

    private void runJob(Job job) {
        log.info("job start run uuid={}", job.getId());
        job.setStatus(JobStatus.RUNING);
        job.setStartTime(LocalDateTime.now());
        job.setModifyTime(LocalDateTime.now());
        jobService.updateById(job);

        ConfigurableSpider spider = configurableSpiderService.getById(job.getSpiderId());

        spider.setFields(configurableSpiderService.parseFields(spider.getFieldsJson()));
        if (spider.getFields().isEmpty()) {
            log.info("runJob error fields empty|uuid={}", job.getId());
            job.setStatus(JobStatus.ERROR);
            job.setEndTime(LocalDateTime.now());
            job.setTimeCost(LocalDateUtil.getSecondInterval(job.getStartTime(), job.getEndTime()));
            jobService.updateById(job);
            return;
        }

        ListPatternProcessor processor = new ListPatternProcessor();
        processor.setMetaInfo(spider);
        Spider.create(processor).setUUID(job.getId()).addUrl(spider.getEntryUrl()).
                addPipeline((resultItems, task) -> {
                    log.info("get page|url={}|uuid={}|spiderId={}", resultItems.getRequest().getUrl(), job.getId(), job.getSpiderId());
                    List<LinkedHashMap<String, String>> resultList = new ArrayList<>();
                    Map<String, Object> resultMap = resultItems.getAll();
                    resultMap.forEach((k, v) -> {
                        List<String> vstr = (ArrayList<String>) v;
                        if (resultList.isEmpty()) {
                            for (int i = 0; i < vstr.size(); i++) {
                                resultList.add(new LinkedHashMap<>());
                            }
                        }
                        for (int i = 0; i < resultList.size(); i++) {
                            Map<String, String> obj = resultList.get(i);
                            obj.put(k, vstr.get(i));
                        }
                    });

                    configurableSpiderService.saveData(resultList, spider, task.getUUID());
                })
                .run();
        job.setStatus(JobStatus.SUCCESS);
        job.setEndTime(LocalDateTime.now());
        job.setTimeCost(LocalDateUtil.getSecondInterval(job.getStartTime(), job.getEndTime()));
        jobService.updateById(job);
        log.info("job end run uuid={}", job.getId());
    }
}
