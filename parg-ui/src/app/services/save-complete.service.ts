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
  students: any;
  studentEnrollments: any;
  games: any;
  physicalReports: any;

  apiUrl = environment.baseUrl;

  constructor(
    private http: HttpClient
  ) { }

  //save the complete data
  saveAllDetails(data: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/student-data/save-all-details`, data);
  }

}
