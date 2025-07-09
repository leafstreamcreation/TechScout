import { Component, Input, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
// import { VotingWidgetComponent } from '../voting-widget/voting-widget.component';
import { NewsItem } from '../../models/news.model';

@Component({
  selector: 'app-news-card',
  standalone: true,
  imports: [CommonModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <article class="news-card card">
      <div class="news-content">
        <div class="news-header">
          <h3 class="news-title" [title]="newsItem.title">
            {{ newsItem.title }}
          </h3>
          <div class="news-meta">
            <span class="source">{{ newsItem.sourceName }}</span>
            <span class="date">{{ formatDate(newsItem.publishDate) }}</span>
          </div>
        </div>
        
        <p class="news-description" *ngIf="newsItem.description">
          {{ newsItem.description }}
        </p>
        
        <div class="news-actions">
          <a 
            [href]="newsItem.sourceUrl" 
            target="_blank" 
            rel="noopener noreferrer"
            class="btn btn-secondary read-more">
            Read More →
          </a>
        </div>
      </div>
    </article>
  `,
  styles: [`
    .news-card {
      height: 100%;
      display: flex;
      flex-direction: column;
      overflow: hidden;
    }
    
    .news-content {
      padding: 1.5rem;
      flex: 1;
      display: flex;
      flex-direction: column;
    }
    
    .news-header {
      margin-bottom: 1rem;
    }
    
    .news-title {
      font-size: 1.1rem;
      font-weight: 600;
      line-height: 1.4;
      margin-bottom: 0.5rem;
      color: #333;
      display: -webkit-box;
      -webkit-line-clamp: 3;
      -webkit-box-orient: vertical;
      overflow: hidden;
    }
    
    .news-meta {
      display: flex;
      justify-content: space-between;
      align-items: center;
      font-size: 0.8rem;
      color: #666;
      gap: 1rem;
    }
    
    .source {
      font-weight: 500;
      background: linear-gradient(135deg, #667eea, #764ba2);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
    }
    
    .date {
      white-space: nowrap;
    }
    
    .news-description {
      flex: 1;
      color: #555;
      line-height: 1.5;
      margin-bottom: 1.5rem;
      display: -webkit-box;
      -webkit-line-clamp: 4;
      -webkit-box-orient: vertical;
      overflow: hidden;
    }
    
    .news-actions {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-top: auto;
      gap: 1rem;
    }
    
    .read-more {
      text-decoration: none;
      font-size: 0.9rem;
      font-weight: 500;
      transition: all 0.2s ease;
      
      &:hover {
        transform: translateX(2px);
      }
    }
    
    @media (max-width: 768px) {
      .news-content {
        padding: 1rem;
      }
      
      .news-actions {
        flex-direction: column;
        gap: 1rem;
        align-items: stretch;
      }
      
      .read-more {
        text-align: center;
      }
    }
  `]
})
export class NewsCardComponent {
  @Input() newsItem!: NewsItem;

  formatDate(dateString: string): string {
    const date = new Date(dateString);
    const now = new Date();
    const diffInHours = Math.floor((now.getTime() - date.getTime()) / (1000 * 60 * 60));
    
    if (diffInHours < 1) {
      return 'Just now';
    } else if (diffInHours < 24) {
      return `${diffInHours}h ago`;
    } else if (diffInHours < 48) {
      return 'Yesterday';
    } else {
      return date.toLocaleDateString();
    }
  }
}
