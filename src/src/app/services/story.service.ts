import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StoryDTO } from '../models/story';

@Injectable({ providedIn: 'root' })
export class StoryService {

  private baseUrl = 'http://localhost:8080/api/stories';

  constructor(private http: HttpClient) {}

  getStories(): Observable<StoryDTO[]> {
    return this.http.get<StoryDTO[]>(this.baseUrl);
  }

  getStory(id: number): Observable<StoryDTO> {
    return this.http.get<StoryDTO>(`${this.baseUrl}/${id}`);
  }

  createStory(story: StoryDTO): Observable<StoryDTO> {
    return this.http.post<StoryDTO>(this.baseUrl, story);
  }

  updateStory(id: number, story: StoryDTO): Observable<StoryDTO> {
    return this.http.put<StoryDTO>(`${this.baseUrl}/${id}`, story);
  }

  deleteStory(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
