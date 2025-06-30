package com.techscout.voting.controller;

import com.techscout.voting.entity.Vote;
import com.techscout.voting.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/votes")
@CrossOrigin(origins = "*")
public class VoteController {
    
    private final VoteService voteService;
    
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }
    
    @PostMapping
    public ResponseEntity<?> submitVote(@RequestBody VoteRequest request) {
        try {
            Vote vote = voteService.submitVote(
                request.newsItemId(), 
                request.value(), 
                request.userId()
            );
            return ResponseEntity.ok(vote);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error submitting vote");
        }
    }
    
    @GetMapping("/news/{newsItemId}")
    public ResponseEntity<VoteCountResponse> getVoteCount(@PathVariable Long newsItemId) {
        try {
            int count = voteService.getVoteCountForNewsItem(newsItemId);
            return ResponseEntity.ok(new VoteCountResponse(newsItemId, count));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // Request/Response DTOs using records (JDK 21 feature)
    public record VoteRequest(Long newsItemId, int value, String userId) {}
    
    public record VoteCountResponse(Long newsItemId, int count) {}
}
