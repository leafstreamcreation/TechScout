export interface NewsItem {
  id: number;
  title: string;
  description: string;
  sourceUrl: string;
  sourceName: string;
  publishDate: string;
}

export interface VoteRequest {
  newsItemId: number;
  value: number; // +1 or -1
  userId: string;
}

export interface Vote {
  id: number;
  newsItemId: number;
  value: number;
  userId: string;
}

export interface VoteCountResponse {
  newsItemId: number;
  count: number;
}
