import { Component, Input, OnInit, ChangeDetectionStrategy, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VoteService } from '../../services/vote.service';
import { VoteRequest } from '../../models/news.model';

@Component({
  selector: 'app-voting-widget',
  standalone: true,
  imports: [CommonModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <div class="voting-widget">
      <button 
        class="vote-btn upvote"
        [class.active]="userVote() === 1"
        [disabled]="isVoting()"
        (click)="vote(1)"
        title="Upvote">
        ▲
      </button>
      
      <span class="vote-count" [class.positive]="voteCount() > 0" [class.negative]="voteCount() < 0">
        {{ voteCount() }}
      </span>
      
      <button 
        class="vote-btn downvote"
        [class.active]="userVote() === -1"
        [disabled]="isVoting()"
        (click)="vote(-1)"
        title="Downvote">
        ▼
      </button>
    </div>
  `,
  styles: [`
    .voting-widget {
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 0.25rem;
      min-width: 40px;
    }
    
    .vote-btn {
      background: rgba(255, 255, 255, 0.1);
      border: 1px solid rgba(0, 0, 0, 0.1);
      border-radius: 6px;
      width: 32px;
      height: 28px;
      cursor: pointer;
      font-size: 0.9rem;
      font-weight: bold;
      transition: all 0.2s ease;
      display: flex;
      align-items: center;
      justify-content: center;
      
      &:hover:not(:disabled) {
        background: rgba(255, 255, 255, 0.2);
        transform: scale(1.05);
      }
      
      &:disabled {
        opacity: 0.5;
        cursor: not-allowed;
        transform: none !important;
      }
      
      &.upvote {
        color: #4CAF50;
        
        &.active {
          background: #4CAF50;
          color: white;
          border-color: #4CAF50;
        }
      }
      
      &.downvote {
        color: #f44336;
        
        &.active {
          background: #f44336;
          color: white;
          border-color: #f44336;
        }
      }
    }
    
    .vote-count {
      font-size: 0.9rem;
      font-weight: 600;
      color: #666;
      min-height: 1.2em;
      display: flex;
      align-items: center;
      transition: color 0.2s ease;
      
      &.positive {
        color: #4CAF50;
      }
      
      &.negative {
        color: #f44336;
      }
    }
  `]
})
export class VotingWidgetComponent implements OnInit {
  @Input() newsItemId!: number;
  
  voteCount = signal<number>(0);
  userVote = signal<number>(0);
  isVoting = signal<boolean>(false);
  
  private userId = 'user-' + Math.random().toString(36).substr(2, 9); // Simple user ID generation

  constructor(private voteService: VoteService) {}

  ngOnInit(): void {
    this.loadVoteCount();
  }

  loadVoteCount(): void {
    this.voteService.getVoteCount(this.newsItemId).subscribe({
      next: (response) => {
        if (response) {
          this.voteCount.set(response.count);
        }
      },
      error: (error) => {
        console.error('Error loading vote count:', error);
      }
    });
  }

  vote(value: number): void {
    if (this.isVoting()) return;
    
    // If clicking the same vote, remove it (set to 0)
    const newVote = this.userVote() === value ? 0 : value;
    
    this.isVoting.set(true);
    
    const voteRequest: VoteRequest = {
      newsItemId: this.newsItemId,
      value: newVote,
      userId: this.userId
    };

    this.voteService.submitVote(voteRequest).subscribe({
      next: (vote) => {
        if (vote) {
          // Update local state
          const voteDifference = newVote - this.userVote();
          this.voteCount.update(count => count + voteDifference);
          this.userVote.set(newVote);
        }
        this.isVoting.set(false);
      },
      error: (error) => {
        console.error('Error submitting vote:', error);
        this.isVoting.set(false);
      }
    });
  }
}
