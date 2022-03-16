package com.aurora.meta.crawler.core;

import com.aurora.meta.crawler.config.MetaCrawlerProperties;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;

/**
 * @author irony
 */
@Slf4j
public class RunningMocker {

    public MetaCrawlerProperties metaCrawlerProperties;

    public <T extends AbstractBaseCrawler> T createXCrawler(Class<? extends AbstractBaseCrawler> crawlerClass) throws Exception {
        Constructor<AbstractBaseCrawler> constructor = (Constructor<AbstractBaseCrawler>) crawlerClass.getDeclaredConstructor(MetaCrawlerProperties.class);
        AbstractBaseCrawler xAbstractBaseCrawler = constructor.newInstance(metaCrawlerProperties);
        return (T) xAbstractBaseCrawler;
    }

    public void run(Class<? extends AbstractBaseCrawler> crawlerClass) throws Exception {
        AbstractBaseCrawler xAbstractBaseCrawler = createXCrawler(crawlerClass);
        String xCrawlerClassName = crawlerClass.getName();
        new Thread(() -> {
            if (xAbstractBaseCrawler.started) {
                return;
            }
            xAbstractBaseCrawler.started = true;
            try {
                log.info("xcrawler " + xCrawlerClassName + ">>>onStart");
                xAbstractBaseCrawler.onStart();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                xAbstractBaseCrawler.onDestory();
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("xcrawler " + xCrawlerClassName + ">>>onDestory");
            try {
                log.info("xcrawler " + xCrawlerClassName + ">>>onStop");
                xAbstractBaseCrawler.onStop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            xAbstractBaseCrawler.started = false;
        }).start();
    }

}
