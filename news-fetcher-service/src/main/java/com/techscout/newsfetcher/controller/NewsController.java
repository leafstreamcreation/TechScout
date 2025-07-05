package com.techscout.newsfetcher.controller;

import com.techscout.newsfetcher.entity.NewsItem;
import com.techscout.newsfetcher.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@CrossOrigin(origins = "*")
public class NewsController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(NewsController.class);
    
    private final NewsService newsService;
    
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }
    
    @GetMapping
    public ResponseEntity<List<NewsItem>> getLatestNews() {
        try {
            List<NewsItem> news = newsService.getLatestNews();
            logger.info("Fetched news items: ", news);
            return ResponseEntity.ok(news);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping("/fetch")
    public ResponseEntity<String> triggerNewsFetch() {
        try {
            newsService.fetchNewsFromAllSources();
            return ResponseEntity.ok("News fetch triggered successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error triggering news fetch");
        }
    }
}
