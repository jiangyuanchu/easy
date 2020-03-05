package com.lomoye.easy;

import com.lomoye.easy.domain.ConfigurableSpider;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 2019/8/27 22:58
 * yechangjun
 */
public class ListPatternProcessor implements PageProcessor {

    private ConfigurableSpider metaInfo;

    private Site site = Site
            .me()
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    @Override
    public void process(Page page) {
        //列表页
        if (page.getUrl().regex(metaInfo.getListRegex()).match()) {
            page.addTargetRequests(page.getHtml().links().regex(metaInfo.getListRegex()).all());
            metaInfo.getFields().forEach((k, v) -> {
                page.putField(k, page.getHtml().xpath(v).all());
            });
        }
    }


    @Override
    public Site getSite() {
        return site;
    }

    public void setMetaInfo(ConfigurableSpider metaInfo) {
        this.metaInfo = metaInfo;
    }
}