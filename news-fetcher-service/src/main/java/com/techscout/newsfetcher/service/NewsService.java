package com.techscout.newsfetcher.service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.rometools.rome.io.FeedException;
import com.techscout.newsfetcher.entity.NewsItem;
import com.techscout.newsfetcher.repository.NewsItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;

@Service
public class NewsService {
    
    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);
    private final NewsItemRepository newsItemRepository;
    private final WebClient webClient;
    
    private static final String TECHCRUNCH_RSS = "https://techcrunch.com/feed/";
    private static final String HACKERNEWS_RSS = "https://news.ycombinator.com/rss";
    
    public NewsService(NewsItemRepository newsItemRepository) {
        this.newsItemRepository = newsItemRepository;
        this.webClient = WebClient.builder().build();
    }
    
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
    public void fetchNewsFromAllSources() {
        logger.info("Starting scheduled news fetch");
        fetchNewsFromSource(TECHCRUNCH_RSS, "TechCrunch");
        fetchNewsFromSource(HACKERNEWS_RSS, "Hacker News");
        logger.info("Completed scheduled news fetch");
    }
    
    private void fetchNewsFromSource(String rssUrl, String sourceName) {
        try {
            logger.info("Fetching news from {} - {}", sourceName, rssUrl);
            
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(rssUrl))
                    .build();
            httpClient.sendAsync(httpRequest, BodyHandlers.ofInputStream())
                    .thenApply(HttpResponse::body)
                    .thenAccept(responseBody -> {
                        SyndFeedInput input = new SyndFeedInput();
                        try {
                            SyndFeed feed = input.build(new XmlReader(responseBody));
                            List<SyndEntry> entries = feed.getEntries();
                            logger.info(entries.toString());
                            int savedCount = 0;
                            for (SyndEntry entry : entries) {
                                if (saveNewsItem(entry, sourceName)) {
                                    savedCount++;
                                }
                            }
                            
                            logger.info("Saved {} new items from {}", savedCount, sourceName);
                        } catch (FeedException | IOException e) {
                            logger.error("Error parsing RSS feed from {} - {}: {}", sourceName, rssUrl, e.getMessage(), e);
                            throw new CompletionException(e);
                        }

                    });
        } catch (Exception e) {
            logger.error("Error fetching news from {} - {}: {}", sourceName, rssUrl, e.getMessage(), e);
        }
    }
    
    private boolean saveNewsItem(SyndEntry entry, String sourceName) {
        try {
            String sourceUrl = entry.getLink();
            
            // Skip if already exists (deduplication)
            if (newsItemRepository.existsBySourceUrl(sourceUrl)) {
                return false;
            }
            
            String title = truncateString(entry.getTitle(), 500);
            String description = entry.getDescription() != null ? 
                truncateString(entry.getDescription().getValue(), 2000) : "";
            
            Instant publishDate = entry.getPublishedDate() != null ? 
                entry.getPublishedDate().toInstant() : Instant.now();
            
            NewsItem newsItem = new NewsItem(title, description, sourceUrl, sourceName, publishDate);
            newsItemRepository.save(newsItem);
            
            return true;
            
        } catch (Exception e) {
            logger.error("Error saving news item: {}", e.getMessage(), e);
            return false;
        }
    }
    
    private String truncateString(String str, int maxLength) {
        return str != null && str.length() > maxLength ? 
            str.substring(0, maxLength - 3) + "..." : str;
    }
    
    public List<NewsItem> getLatestNews() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by("publish_date").descending());
        return newsItemRepository.findLatestNews(pageable);
    }
}
