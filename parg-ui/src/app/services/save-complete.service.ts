import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GameService } from '../game/services/game.service';
import { PhysicalReportService } from '../physical-report/services/physical-report.service';
import { StudentEnrollmentService } from '../student-enrollment/services/student-enrollment.service';
import { StudentService } from '../student/services/student.service';
import { catchError, forkJoin, map, Observable } from 'rxjs';
import { environment } from '../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class SaveCompleteService {

  apiUrl = environment.baseUrl;

  constructor(
    private http: HttpClient
  ) { }

  //save the complete data
  saveAllDetails(data: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/student-data/save-all-details`, data);
  }

  //get the complete data
  getStudentCompleteDataByClassId(classId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/student/getStudentCompleteData/${classId}`);
  }

  //update the complete data
  updateAllDetails(data: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/student-data/update-multiple-students`, data);
  }

  //delete the complete data
  // Delete the complete data
  deleteAllDetails(data: any): Observable<any> {
    const options = {
      body: data // Pass the body here for DELETE request
    };
    return this.http.delete<any>(`${this.apiUrl}/student-data/delete-multiple-students`, options);
  }

  //generate bmi details
  generateBMIDetails(data: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/bmi/bmi-details`, data);
  }

  //saving student details by reading from excel file
  saveExcelData(examId: number, data: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/excel/saveExcelData/${examId}`, data);
  }


}
