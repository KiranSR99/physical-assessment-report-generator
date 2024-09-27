import { Injectable } from '@angular/core';
import { environment } from '../../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PhysicalReportService {
  apiUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  savePhysicalReports(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/physicalReport/savePhysicalReports`, data);
  }

  getAllPhysicalReportData(): Observable<any> {
    return this.http.get(`${this.apiUrl}/physicalReport/getAllPhysicalReports?page=0&size=10`);
  }
}
