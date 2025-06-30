# TechScout - Tech News Aggregator

A microservices-based tech news aggregator built with Angular 16+, Spring Boot 3, and PostgreSQL 15+.

## Architecture

- **Frontend**: Angular 16+ with standalone components, TypeScript 5+
- **News Fetcher Service**: Spring Boot 3 (Port 8081) - Fetches news from RSS feeds
- **Voting Service**: Spring Boot 3 (Port 8082) - Handles voting functionality
- **Databases**: PostgreSQL 15+ (separate databases for each service)
- **Orchestration**: Docker Compose

## Tech Stack

- **Backend**: OpenJDK 21, Spring Boot 3, Spring Data JPA, WebClient
- **Frontend**: Angular 16+, TypeScript 5+, SCSS, Standalone Components
- **Database**: PostgreSQL 15+
- **Containerization**: Docker, Docker Compose
- **RSS Feeds**: TechCrunch, Hacker News

## Features

- **Scheduled News Fetching**: Hourly automated fetching from RSS feeds
- **Real-time Voting**: Upvote/downvote news items with live count updates
- **Responsive Design**: Modern, mobile-first UI design
- **Microservices Architecture**: Loosely coupled services with health checks
- **JDK 21 Features**: Records, pattern matching, and other modern Java features

## Prerequisites

- Docker and Docker Compose
- OpenJDK 21 (for local development)
- Node.js 18+ (for frontend development)

## Quick Start

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd TechScout
   ```

2. **Start all services with Docker Compose**
   ```bash
   docker-compose up --build
   ```

3. **Access the application**
   - Frontend: http://localhost:4200
   - News API: http://localhost:8081/news
   - Voting API: http://localhost:8082/votes

## Services

### News Fetcher Service (Port 8081)

**Features:**
- Hourly scheduled RSS feed fetching
- News deduplication by source URL
- RESTful API for news retrieval

**Endpoints:**
- `GET /news` - Get latest 20 news items
- `POST /news/fetch` - Manually trigger news fetch

**Database:** PostgreSQL (`newsdb`)

### Voting Service (Port 8082)

**Features:**
- Vote submission (+1/-1)
- Vote count aggregation
- User vote tracking

**Endpoints:**
- `POST /votes` - Submit a vote
- `GET /votes/news/{newsItemId}` - Get vote count for news item

**Database:** PostgreSQL (`votedb`)

### Frontend (Port 4200)

**Features:**
- News listing with pagination
- Real-time voting widget
- Responsive card layout
- Modern glassmorphism design

**Components:**
- `NewsListComponent` - Displays news items
- `NewsCardComponent` - Individual news card
- `VotingWidgetComponent` - Upvote/downvote interface

## Development

### Local Development Setup

1. **Backend Services**
   ```bash
   # News Fetcher Service
   cd news-fetcher-service
   ./mvnw spring-boot:run

   # Voting Service
   cd voting-service
   ./mvnw spring-boot:run
   ```

2. **Frontend**
   ```bash
   cd frontend
   npm install
   npm start
   ```

3. **Database**
   ```bash
   # Start only databases
   docker-compose up newsdb votedb
   ```

### Building Individual Services

```bash
# News Fetcher Service
cd news-fetcher-service
./mvnw clean package

# Voting Service
cd voting-service
./mvnw clean package

# Frontend
cd frontend
npm run build
```

## API Documentation

### News Service API

**Get Latest News**
```http
GET /news
Response: NewsItem[]
```

**NewsItem Schema:**
```json
{
  "id": 1,
  "title": "News Title",
  "description": "News description...",
  "sourceUrl": "https://...",
  "sourceName": "TechCrunch",
  "publishDate": "2025-06-29T10:00:00Z"
}
```

### Voting Service API

**Submit Vote**
```http
POST /votes
Content-Type: application/json

{
  "newsItemId": 1,
  "value": 1,  // +1 or -1
  "userId": "user-123"
}
```

**Get Vote Count**
```http
GET /votes/news/{newsItemId}
Response: {"newsItemId": 1, "count": 5}
```

## Configuration

### Environment Variables

**News Fetcher Service:**
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

**Voting Service:**
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

**Frontend:**
- `NEWS_API_URL`
- `VOTING_API_URL`

## Docker Compose Services

- `newsdb` - PostgreSQL for news service
- `votedb` - PostgreSQL for voting service
- `news-fetcher` - Spring Boot news service
- `voting-service` - Spring Boot voting service
- `frontend` - Angular application with Nginx

## Health Checks

All services include health checks:
- Database: PostgreSQL `pg_isready`
- Services: Spring Boot Actuator `/actuator/health`
- Frontend: Nginx status

## RSS Sources

- **TechCrunch**: https://techcrunch.com/feed/
- **Hacker News**: https://news.ycombinator.com/rss

## Monitoring

- Spring Boot Actuator endpoints for service health
- Docker Compose health checks
- PostgreSQL connection monitoring

## Troubleshooting

**Service won't start:**
1. Check Docker logs: `docker-compose logs [service-name]`
2. Verify database connectivity
3. Check port conflicts

**Database connection issues:**
1. Ensure PostgreSQL containers are healthy
2. Verify environment variables
3. Check network connectivity

**Frontend API calls failing:**
1. Verify backend services are running
2. Check CORS configuration
3. Verify API URLs in environment files

## License

This project is licensed under the MIT License.
