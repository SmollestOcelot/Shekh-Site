import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DoodleDTO } from '../models/story';

@Injectable({ providedIn: 'root' })
export class DoodleService {

  private baseUrl = 'http://localhost:8080/api/doodles';

  constructor(private http: HttpClient) {}

  getDoodle(): Observable<DoodleDTO[]> {
    return this.http.get<DoodleDTO[]>(this.baseUrl);
  }

  getDoodle(id: number): Observable<DoodleDTO> {
    return this.http.get<DoodleDTO>(`${this.baseUrl}/${id}`);
  }

  createDoodle(doodle: DoodleDTO): Observable<DoodleDTO> {
    return this.http.post<DoodleDTO>(this.baseUrl, doodle);
  }

  updateDoodle(id: number, doodle: DoodleDTO): Observable<DoodleDTO> {
    return this.http.put<DoodleDTO>(`${this.baseUrl}/${id}`, doodle);
  }

  deleteDoodle(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
