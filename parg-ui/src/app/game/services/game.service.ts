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

  saveGame(game: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/physicalTest/savePhysicalTest`, game);
  }

  getAllGames(): Observable<any> {
    return this.http.get(`${this.apiUrl}/physicalTest/getAllPhysicalTests`);
  }

  getGameById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/physicalTest/getPhysicalTestById/${id}`);
  }

  getAllGamesByClassId(classId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/physicalTest/getAllByClassId/${classId}`);
  }

  updateGame(id: number, game: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/physicalTest/updatePhysicalTest/${id}`, game);
  }

  deleteGame(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/physicalTest/deletePhysicalTest/${id}`);
  }
}
