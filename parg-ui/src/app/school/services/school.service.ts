import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environment/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SchoolService {
  apiUrl: string = environment.baseUrl;

  constructor(private http: HttpClient) { }

  saveSchool(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/school/saveSchool`, data);
  }

  getAllSchools(): Observable<any> {
    return this.http.get(`${this.apiUrl}/school/getAllSchools`);
  }

  getSchoolById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/school/getSchoolById/${id}`);
  }

  updateSchool(data: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/school/updateSchool`, data);
  }

  deleteSchool(id: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/school/deleteSchool/${id}`, {});
  }

}
