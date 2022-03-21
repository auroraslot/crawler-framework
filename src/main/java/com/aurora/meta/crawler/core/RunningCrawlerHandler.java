package com.aurora.meta.crawler.core;

import com.aurora.meta.crawler.config.MetaCrawlerProperties;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;

/**
 * @author irony
 */
@Slf4j
public class RunningCrawlerHandler {
    public static void run(AbstractBaseCrawler crawler) {
        String className = crawler.getClass().getName();
        new Thread(() -> {
            if (crawler.started) {
                return;
            }
            crawler.started = true;
            try {
                log.info("xcrawler " + className + ">>>onStart");
                crawler.onStart();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                crawler.onDestory();
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("xcrawler " + className + ">>>onDestory");
            try {
                log.info("xcrawler " + className + ">>>onStop");
                crawler.onStop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            crawler.started = false;
        }).start();
    }
}
