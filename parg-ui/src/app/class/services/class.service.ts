import { Injectable } from '@angular/core';
import { environment } from '../../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClassService {

  apiUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  addClasses(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/class/saveClass`, data);
  }

  getAllClasses(): Observable<any> {
    return this.http.get(`${this.apiUrl}/class/getAllClasses`);
  }

  getClassById(id: any): Observable<any> {
    return this.http.get(`${this.apiUrl}/class/getClassById/${id}`);
  }

  getClassBySchoolId(schoolId: any): Observable<any> {
    return this.http.get(`${this.apiUrl}/class/getClassBySchoolId/${schoolId}`);
  }

  getClassesByExamId(examId: any): Observable<any> {
    return this.http.get(`${this.apiUrl}/class/getClassesByExamId/${examId}`);
  }

  updateClass(id: number, data: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/class/updateClass/${id}`, data);
  }

  deleteClass(id: any): Observable<any> {
    return this.http.delete(`${this.apiUrl}/class/deleteClass/${id}`);
  }
}
