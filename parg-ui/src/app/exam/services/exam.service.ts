import { Injectable } from '@angular/core';
import { environment } from '../../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExamService {
  apiUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  //Adding exam
  addExam(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/exam/saveExam`, data);
  }

  //Getting all exams
  getAllExams(): Observable<any> {
    return this.http.get(`${this.apiUrl}/exam/getAllExams`);
  }

  //Getting all exams of school
  getAllExamsOfSchool(schoolId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/exam/getAllExamsOfSchool/${schoolId}`);
  }

  //Getting exam by id
  getExamById(examId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/exam/getExamById/${examId}`);
  }

  //Updating exam by id
  updateExam(examId: number, data: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/exam/updateExam/${examId}`, data);
  }

  //Deleting exam by id
  deleteExam(examId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/exam/deleteExam/${examId}`);
  }


}
