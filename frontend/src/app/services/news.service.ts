import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, of } from 'rxjs';
import { NewsItem } from '../models/news.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NewsService {
  private readonly apiUrl = environment.newsApiUrl;

  constructor(private http: HttpClient) {}
//wtf: WHAT THE FUCK
  getLatestNews(): Observable<NewsItem[]> {
    return this.http.get<NewsItem[]>(this.apiUrl).pipe(
      catchError(error => {
        console.error('Error fetching news:', error);
        return of([]); // Return empty array on error
      })
    );
  }

  triggerNewsFetch(): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/fetch`, {}).pipe(
      catchError(error => {
        console.error('Error triggering news fetch:', error);
        return of('Error triggering news fetch');
      })
    );
  }
}
