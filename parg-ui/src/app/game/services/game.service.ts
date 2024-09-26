import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environment/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  apiUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  saveGames(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/physicalTestPerformanceMetric/saveMultiplePhysicalTestPerformanceMetrics`, data);
  }

  getAllGamesData(): Observable<any> {  
    return this.http.get(`${this.apiUrl}/physicalTestPerformanceMetric/getAllPhysicalTestPerformanceMetrics`);
  }
}
