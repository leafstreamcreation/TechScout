import { Component, OnInit, ChangeDetectionStrategy, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NewsCardComponent } from '../news-card/news-card.component';
import { NewsService } from '../../services/news.service';
import { NewsItem } from '../../models/news.model';

@Component({
  selector: 'app-news-list',
  standalone: true,
  imports: [CommonModule, NewsCardComponent],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <div class="news-list">
      <div class="news-list-header">
        <h2>Latest Tech News</h2>
        <button 
          class="btn btn-primary refresh-btn"
          (click)="refreshNews()"
          [disabled]="isLoading()">
          <span *ngIf="!isLoading()">🔄 Refresh</span>
          <span *ngIf="isLoading()">Loading...</span>
        </button>
      </div>
      
      <div *ngIf="isLoading() && newsItems().length === 0" class="loading">
        <div class="spinner"></div>
      </div>
      
      <div *ngIf="!isLoading() && newsItems().length === 0" class="no-news">
        <h3>No news available</h3>
        <p>Try refreshing to fetch the latest news.</p>
      </div>
      
      <div class="news-grid" *ngIf="newsItems().length > 0">
        <app-news-card 
          *ngFor="let newsItem of newsItems(); trackBy: trackByNewsId"
          [newsItem]="newsItem">
        </app-news-card>
      </div>
    </div>
  `,
  styles: [`
    .news-list {
      padding: 1rem 0;
    }
    
    .news-list-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 2rem;
      
      h2 {
        color: white;
        font-size: 2rem;
        font-weight: 600;
        text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
      }
      
      .refresh-btn {
        font-size: 0.9rem;
        white-space: nowrap;
      }
    }
    
    .news-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
      gap: 1.5rem;
    }
    
    .no-news {
      text-align: center;
      padding: 3rem;
      color: white;
      
      h3 {
        margin-bottom: 1rem;
        font-size: 1.5rem;
      }
      
      p {
        opacity: 0.8;
      }
    }
    
    @media (max-width: 768px) {
      .news-list-header {
        flex-direction: column;
        gap: 1rem;
        text-align: center;
        
        h2 {
          font-size: 1.5rem;
        }
      }
      
      .news-grid {
        grid-template-columns: 1fr;
        gap: 1rem;
      }
    }
  `]
})
export class NewsListComponent implements OnInit {
  newsItems = signal<NewsItem[]>([]);
  isLoading = signal<boolean>(false);

  constructor(private newsService: NewsService) {}

  ngOnInit(): void {
    this.loadNews();
  }

  loadNews(): void {
    this.isLoading.set(true);
    this.newsService.getLatestNews().subscribe({
      next: (news) => {
        this.newsItems.set(news);
        this.isLoading.set(false);
      },
      error: (error) => {
        console.error('Error loading news:', error);
        this.isLoading.set(false);
      }
    });
  }

  refreshNews(): void {
    this.loadNews();
  }

  trackByNewsId(index: number, item: NewsItem): number {
    return item.id;
  }
}
