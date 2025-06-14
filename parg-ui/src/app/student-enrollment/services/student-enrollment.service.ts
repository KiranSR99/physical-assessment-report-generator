import { Injectable } from '@angular/core';
import { environment } from '../../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StudentEnrollmentService {
  apiUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  saveStudentEnrollment(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/studentEnrollment/saveStudentEnrollment`, data);
  }

  getAllStudentEnrollmentData(): Observable<any> {
    return this.http.get(`${this.apiUrl}/studentEnrollment/getAllStudentEnrollments?page=0&size=10`);
  }
}
