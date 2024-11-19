import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class ClusteringService {

  apiUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  //Clustering students into groups according to BMI
  clusterStudents(data: any): any {
    return this.http.post<any>(`${this.apiUrl}/clustering/kmeans`, data);
  }
}
