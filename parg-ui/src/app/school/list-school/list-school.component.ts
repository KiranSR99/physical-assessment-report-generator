import { Component, OnInit } from '@angular/core';
import { SchoolService } from '../services/school.service';
import { Router } from '@angular/router';
import { ToastService } from '../../services/toast-service.service';

@Component({
  selector: 'app-list-school',
  templateUrl: './list-school.component.html',
  styleUrl: './list-school.component.scss'
})
export class ListSchoolComponent implements OnInit {

  schools: any[] = [];

  constructor(
    private schoolService: SchoolService,
    private router: Router,
    private toast: ToastService
  ) { }

  ngOnInit(): void {
    this.getAllSchools();
  }

  getAllSchools(): void{
    this.schoolService.getAllSchools().subscribe({
      next: (response: any) => {
        this.schools = response.data;
      },
      error: (error: any) => {
        console.log(error.error.message);
      }
    })
  }

  editSchool(id: any): void{
    this.router.navigate(['/school/update', id]);
  }

  deleteSchool(id: any): void{
    this.schoolService.deleteSchool(id).subscribe({
      next: (response: any) => {
        this.toast.showSuccess("School deleted successfully.");
        this.getAllSchools();
      },
      error: (error: any) => {
        console.log(error.error.message);
      }
    })
  }

  schoolDetail(id: any): void{
    this.router.navigate([`/school/school-detail/${id}`]);
  }

}
