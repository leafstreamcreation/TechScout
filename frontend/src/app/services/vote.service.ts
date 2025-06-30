import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, of } from 'rxjs';
import { VoteRequest, Vote, VoteCountResponse } from '../models/news.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class VoteService {
  private readonly apiUrl = environment.votingApiUrl;

  constructor(private http: HttpClient) {}

  submitVote(voteRequest: VoteRequest): Observable<Vote | null> {
    return this.http.post<Vote>(this.apiUrl, voteRequest).pipe(
      catchError((error: any) => {
        console.error('Error submitting vote:', error);
        return of(null); // Return null on error
      })
    );
  }

  getVoteCount(newsItemId: number): Observable<VoteCountResponse | null> {
    return this.http.get<VoteCountResponse>(`${this.apiUrl}/news/${newsItemId}`).pipe(
      catchError((error: any) => {
        console.error('Error fetching vote count:', error);
        return of({ newsItemId, count: 0 }); // Return default count on error
      })
    );
  }
}
