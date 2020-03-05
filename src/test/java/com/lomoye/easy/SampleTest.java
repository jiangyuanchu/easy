package com.lomoye.easy;

import com.lomoye.easy.dao.JobMapper;
import com.lomoye.easy.domain.Job;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

    @Autowired
    private JobMapper jobMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<Job> jobList = jobMapper.selectList(null);
        Assert.assertEquals(2, jobList.size());
        jobList.forEach(System.out::println);
    }

}