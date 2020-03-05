package com.lomoye.easy;

import com.lomoye.easy.backend.JobBackendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 杭州蓝诗网络科技有限公司 版权所有 © Copyright 2018<br>
 * 后台任务的入口 方便注入service
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2018/8/31 13:35 <br>
 * @Author: <a href="yechangjun@quannengzhanggui.com">lomoye</a>
 */
@Service
public class JobEntry {
    @Autowired
    private JobBackendService jobBackendService;

    public void start() throws Exception {
        jobBackendService.start();
    }
}
