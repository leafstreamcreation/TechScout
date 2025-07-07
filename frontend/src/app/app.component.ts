import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NewsListComponent } from './components/news-list/news-list.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, NewsListComponent],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <div class="app">
      <header class="header">
        <div class="container">
          <div class="header-content">
            <div>
              <h1 class="logo">Muninn</h1>
              <p class="subtitle">Tech News Aggregator</p>
            </div>
          </div>
        </div>
      </header>
      
      <main class="main-content">
        <div class="container">
          <app-news-list></app-news-list>
        </div>
      </main>
    </div>
  `,
  styles: [`
    .app {
      min-height: 100vh;
    }
  `]
})
export class AppComponent implements OnInit {
  title = 'TechScout';

  ngOnInit(): void {
    console.log('TechScout App initialized');
  }
}
