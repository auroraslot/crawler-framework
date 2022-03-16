package com.aurora.meta.crawler.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author irony
 */
@Component
@ConfigurationProperties(prefix = "meta.crawler")
public class MetaCrawlerProperties {
    private String proxyServer;

    private int proxyPort;

    private int threadPoolNumbers = 100;

    public String getProxyServer() {
        return proxyServer;
    }

    public void setProxyServer(String proxyServer) {
        this.proxyServer = proxyServer;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public int getThreadPoolNumbers() {
        return threadPoolNumbers;
    }

    public void setThreadPoolNumbers(int threadPoolNumbers) {
        this.threadPoolNumbers = threadPoolNumbers;
    }
}
