package com.lomoye.easy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lomoye.easy.domain.ConfigurableSpider;
import com.lomoye.easy.exception.BusinessException;
import com.lomoye.easy.exception.ErrorCode;
import com.lomoye.easy.model.ListPatternModel;
import com.lomoye.easy.model.common.ResultData;
import com.lomoye.easy.model.common.ResultPagedList;
import com.lomoye.easy.model.search.ConfigurableSpiderSearchModel;
import com.lomoye.easy.service.ConfigurableSpiderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 2019/8/28 15:25
 * yechangjun
 * {
 * "entryUrl": "http://www.dianshangleida.com/list/appeal/1",
 * "listRegex": "http://www\\.dianshangleida\\.com/list/appeal/\\d+",
 * "fields": {"nick":"//*[@id='tam_newlist']/li/a/p[2]/span/text()",
 * "search_num":"//*[@id='tam_newlist']/li/a/p[3]/span/text()" },
 * "tableName": "ww_black_member"
 * }
 * <p>
 * {
 * "entryUrl": "http://www.xntk.net/mlist.php?t_id=0&page=0",
 * "listRegex": "http://www\\.xntk\\.net/mlist\\.php\\?t_id=0&page=\\d+",
 * "fields": {"book_name":"//*[@id='list']/tbody/tr/td[2]/a/text()",
 * "last_chapter":"//*[@id='list']/tbody/tr/td[3]/a/text()",
 * "type":"//*[@id='list']/tbody/tr/td[4]/font/text()",
 * "author":"//*[@id='list']/tbody/tr/td[5]/a/text()",
 * "update_time":"//*[@id='list']/tbody/tr/td[6]/small/text()" },
 * "tableName": "book"
 * }
 */
@RestController
@RequestMapping("/configurable-spider")
@Api(tags = "可配置化爬虫", description = "可配置化爬虫 lomoye")
public class ConfigurableSpiderController {

    @Autowired
    private ConfigurableSpiderService configurableSpiderService;

    @PostMapping
    @ApiOperation("增加一个可配置化爬虫")
    @ResponseBody
    public ResultData<ConfigurableSpider> addConfigurableSpider(@RequestBody ListPatternModel model) {
        if (model == null) {
            throw new BusinessException(ErrorCode.PARAMETER_ILLEGAL, "提交参数不能为空");
        }
        if (Strings.isEmpty(model.getEntryUrl())) {
            throw new BusinessException(ErrorCode.PARAMETER_ILLEGAL, "入口页不能为空");
        }
        if (Strings.isEmpty(model.getListRegex())) {
            throw new BusinessException(ErrorCode.PARAMETER_ILLEGAL, "列表页正则表达式不能为空");
        }
        if (Strings.isEmpty(model.getTableName())) {
            throw new BusinessException(ErrorCode.PARAMETER_ILLEGAL, "保存用的表名不能为空");
        }

        ConfigurableSpider spider = ConfigurableSpider.valueOf(model);
        configurableSpiderService.save(spider);

        return new ResultData<>(spider);
    }

    @PostMapping("/search")
    @ApiOperation("分页搜索")
    @ResponseBody
    public ResultPagedList<ConfigurableSpider> searchConfigurableSpider(@RequestBody ConfigurableSpiderSearchModel searchModel) {
        IPage<ConfigurableSpider> page = new Page<>(searchModel.getPageNo(), searchModel.getPageSize());
        QueryWrapper<ConfigurableSpider> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(Strings.isNotEmpty(searchModel.getName()), ConfigurableSpider::getName, searchModel.getName())
                .like(Strings.isNotEmpty(searchModel.getTableName()), ConfigurableSpider::getTableName, searchModel.getTableName())
                .orderByDesc(ConfigurableSpider::getCreateTime);


        page = configurableSpiderService.page(page, queryWrapper);
        return new ResultPagedList<>(page.getRecords(), page.getTotal(), searchModel);
    }
}
