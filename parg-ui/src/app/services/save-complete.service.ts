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
    private http: HttpClient,
    private studentService: StudentService,
    private studentEnrollmentService: StudentEnrollmentService,
    private gameService: GameService,
    private physicalReportService: PhysicalReportService
  ) { }


  saveStudents(data: any): void {
    this.studentService.saveStudents(data).subscribe({
      next: (response: any) => {

      }
    });
  }

  saveStudentEnrollment(data: any): void {
    this.studentEnrollmentService.saveStudentEnrollment(data).subscribe();
  }

  saveGamesData(data: any): void {
    this.gameService.saveGames(data).subscribe();
  }

  savePhysicalReports(data: any): void {
    this.physicalReportService.savePhysicalReports(data).subscribe();
  }

  getAllStudentsByClassId(classId: number): void {
    this.studentService.getStudentCompleteDataByClassId(classId).subscribe({
      next: (response: any) => {
        this.students = response.data;
      }
    });
  }


  saveAllData(formData: any): Observable<any> {
    // Create observables for each API call
    const saveStudents$ = this.http.post(`${this.apiUrl}/student/saveStudents`, formData.students);
    const saveEnrollments$ = this.http.post(`${this.apiUrl}/studentEnrollment/saveStudentEnrollment`, formData.studentEnrollments);
    const savePhysicalReports$ = this.http.post(`${this.apiUrl}/physicalReport/savePhysicalReports`, formData.physicalReports);
    const savePerformanceMetrics$ = this.http.post(`${this.apiUrl}/physicalTestPerformanceMetric/saveMultiplePhysicalTestPerformanceMetrics`, formData.physicalTestPerformanceMetrics);

    // Use forkJoin to make all API calls concurrently
    return forkJoin({
      students: saveStudents$,
      enrollments: saveEnrollments$,
      physicalReports: savePhysicalReports$,
      performanceMetrics: savePerformanceMetrics$
    }).pipe(
      map((responses: any) => {
        // Process the responses if needed
        console.log('All data saved successfully', responses);
        return responses;
      }),
      catchError((error: any) => {
        console.error('Error saving data', error);
        throw error; // Re-throw the error so it can be handled in the component
      })
    );
  }


}
