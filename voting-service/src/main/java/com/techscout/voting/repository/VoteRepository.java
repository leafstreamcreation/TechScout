package com.techscout.voting.repository;

import com.techscout.voting.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    
    @Query("SELECT SUM(v.value) FROM Vote v WHERE v.newsItemId = :newsItemId")
    Integer getVoteCountByNewsItemId(@Param("newsItemId") Long newsItemId);
    
    List<Vote> findByNewsItemId(Long newsItemId);
    
    Optional<Vote> findByNewsItemIdAndUserId(Long newsItemId, String userId);
    
    boolean existsByNewsItemIdAndUserId(Long newsItemId, String userId);
}
