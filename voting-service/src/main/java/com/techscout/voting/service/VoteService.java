package com.techscout.voting.service;

import com.techscout.voting.entity.Vote;
import com.techscout.voting.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VoteService {
    
    private static final Logger logger = LoggerFactory.getLogger(VoteService.class);
    private final VoteRepository voteRepository;
    
    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }
    
    @Transactional
    public Vote submitVote(Long newsItemId, int value, String userId) {
        // Validate vote value
        if (value != 1 && value != -1) {
            throw new IllegalArgumentException("Vote value must be +1 or -1");
        }
        
        // Check if user already voted for this news item
        Optional<Vote> existingVote = voteRepository.findByNewsItemIdAndUserId(newsItemId, userId);
        
        if (existingVote.isPresent()) {
            // Update existing vote
            Vote updatedVote = new Vote(existingVote.get().id(), newsItemId, value, userId);
            logger.info("Updating vote for user {} on news item {}", userId, newsItemId);
            return voteRepository.save(updatedVote);
        } else {
            // Create new vote
            Vote newVote = new Vote(newsItemId, value, userId);
            logger.info("Creating new vote for user {} on news item {}", userId, newsItemId);
            return voteRepository.save(newVote);
        }
    }
    
    public int getVoteCountForNewsItem(Long newsItemId) {
        Integer count = voteRepository.getVoteCountByNewsItemId(newsItemId);
        return count != null ? count : 0;
    }
}
