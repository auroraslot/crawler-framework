package com.aurora.meta.crawler.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aurora.meta.crawler.config.MetaCrawlerProperties;
import com.aurora.meta.crawler.util.CrawlerUtil;
import com.deep007.goniub.DefaultHttpDownloader;
import com.deep007.goniub.DownloadVerifier;
import com.deep007.goniub.request.PageRequest;
import com.deep007.goniub.response.Page;
import com.deep007.goniub.util.BlockTimer;
import com.deep007.goniub.util.CountableThreadPool;
import lombok.Data;
import org.jsoup.nodes.Document;

/**
 * @author irony
 */
@Data
public abstract class AbstractBaseCrawler {

    protected MetaCrawlerProperties metaCrawlerProperties;

    protected CountableThreadPool threadPool;

    public volatile boolean started = false;

    private BlockTimer blockTimer;

    protected DefaultHttpDownloader httpDownloader = new DefaultHttpDownloader() {
        @Override
        public Page download(PageRequest request) {
            BlockTimer blockTimer = AbstractBaseCrawler.this.blockTimer;
            if (blockTimer != null) {
                blockTimer.toNext();
            }
            return super.download(request);
        }
    };

    public void setRequestInterval(long millis) {
        if (millis > 0) {
            blockTimer = new BlockTimer(millis);
        }
    }

    public AbstractBaseCrawler(MetaCrawlerProperties metaCrawlerProperties) {
        this.metaCrawlerProperties = metaCrawlerProperties;
    }

    public void setUserAgent(String userAgent) {
        this.httpDownloader.setUserAgent(userAgent);
    }

    public interface CrawlerExecutor {
        void execute(MetaCrawlerProperties metaCrawlerProperties) throws Exception;
    }

    public void asyncExecute(CrawlerExecutor crawlerExecutor) {
        asyncExecute(crawlerExecutor, 0);
    }

    public synchronized void asyncExecute(CrawlerExecutor crawlerExecutor, int priority) {
        int threadPoolNumbers = metaCrawlerProperties.getThreadPoolNumbers();
        if (threadPool == null && threadPoolNumbers > 0) {
            threadPool = new CountableThreadPool(threadPoolNumbers);
        }
        if (threadPool != null && crawlerExecutor != null) {
            threadPool.execute(() -> {
                try {
                    crawlerExecutor.execute(metaCrawlerProperties);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, priority);
        }
    }

    public Document requestJsoupDocument(String url) {
        return CrawlerUtil.getJsoupDocument(httpDownloader.download(url));
    }

    public Document requestJsoupDocument(PageRequest pageRequest) {
        return CrawlerUtil.getJsoupDocument(httpDownloader.download(pageRequest));
    }

    public Document requestJsoupDocumentWithVrify(String url, int maxRetry, String... verifyPattern) {
        return CrawlerUtil.getJsoupDocument(httpDownloader.downloadWithVrify(url, maxRetry, verifyPattern));
    }

    public Document requestJsoupDocumentWithVrify(PageRequest pageRequest, int maxRetry, String... verifyPattern) {
        return CrawlerUtil.getJsoupDocument(httpDownloader.downloadWithVrify(pageRequest, maxRetry, verifyPattern));
    }

    public Document requestJsoupDocumentWithVrify(PageRequest pageRequest, int maxRetry, DownloadVerifier downloadVerifier) {
        return CrawlerUtil.getJsoupDocument(httpDownloader.downloadWithVrify(pageRequest, maxRetry, downloadVerifier));
    }

    public JSONObject requestJSONObject(String url) {
        return CrawlerUtil.getJSONObject(httpDownloader.download(url));
    }

    public JSONObject requestJSONObjectWithVrify(String url, int maxRetry, String... verifyPattern) {
        return CrawlerUtil.getJSONObject(httpDownloader.downloadWithVrify(url, maxRetry, verifyPattern));
    }

    public JSONObject requestJSONObject(PageRequest pageRequest) {
        return CrawlerUtil.getJSONObject(httpDownloader.download(pageRequest));
    }

    public JSONObject requestJSONObjectWithVrify(PageRequest pageRequest, int maxRetry, String... verifyPattern) {
        return CrawlerUtil.getJSONObject(httpDownloader.downloadWithVrify(pageRequest, maxRetry, verifyPattern));
    }

    public JSONObject requestJSONObjectWithVrify(PageRequest pageRequest, int maxRetry, DownloadVerifier downloadVerifier) {
        return CrawlerUtil.getJSONObject(httpDownloader.downloadWithVrify(pageRequest, maxRetry, downloadVerifier));
    }

    public JSONArray requestJSONArray(String url) {
        return CrawlerUtil.getJSONArray(httpDownloader.download(url));
    }

    public JSONArray requestJSONArrayWithVrify(String url, int maxRetry, String... verifyPattern) {
        return CrawlerUtil.getJSONArray(httpDownloader.downloadWithVrify(url, maxRetry, verifyPattern));
    }

    public JSONArray requestJSONArray(PageRequest pageRequest) {
        return CrawlerUtil.getJSONArray(httpDownloader.download(pageRequest));
    }

    public JSONArray requestJSONArrayWithVrify(PageRequest pageRequest, int maxRetry, String... verifyPattern) {
        return CrawlerUtil.getJSONArray(httpDownloader.downloadWithVrify(pageRequest, maxRetry, verifyPattern));
    }

    public JSONArray requestJSONArrayWithVrify(PageRequest pageRequest, int maxRetry, DownloadVerifier downloadVerifier) {
        return CrawlerUtil.getJSONArray(httpDownloader.downloadWithVrify(pageRequest, maxRetry, downloadVerifier));
    }

    public abstract void onStart();

    public abstract void onStop();

    public void onDestory() {
        if (threadPool != null) {
            threadPool.shutdown();
        }
    }
}

