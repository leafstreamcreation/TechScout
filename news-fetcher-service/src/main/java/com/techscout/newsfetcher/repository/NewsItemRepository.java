package com.techscout.newsfetcher.repository;

import com.techscout.newsfetcher.entity.NewsItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsItemRepository extends JpaRepository<NewsItem, Long> {
     
    @Query("SELECT n FROM NewsItem n ORDER BY n.publishDate DESC")
    List<NewsItem> findLatestNews(Pageable pageable);
    
    Optional<NewsItem> findBySourceUrl(String sourceUrl);
    
    boolean existsBySourceUrl(String sourceUrl);
}
