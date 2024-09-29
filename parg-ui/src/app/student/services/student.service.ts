import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environment/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  apiUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  saveStudents(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/student/saveStudents`, data);
  }

}
