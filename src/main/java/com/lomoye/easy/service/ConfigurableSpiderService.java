package com.lomoye.easy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lomoye.easy.domain.ConfigurableSpider;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 2019/8/30 15:11
 * yechangjun
 * 通用配置爬虫保存爬取的数据
 */
public interface ConfigurableSpiderService extends IService<ConfigurableSpider> {
    /**
     * 保存爬取的数据
     *
     * @param datas 数据对象，每个map对应一个数据
     * @param spider 用户的爬虫配置
     * @param uuid 任务id
     */
    void saveData(List<LinkedHashMap<String, String>> datas, ConfigurableSpider spider, String uuid);

    LinkedHashMap<String,String> parseFields(String fieldsJson);
}
