import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SchoolService } from '../services/school.service';
import { ToastService } from '../../services/toast-service.service';

@Component({
  selector: 'app-school-detail',
  templateUrl: './school-detail.component.html',
  styleUrl: './school-detail.component.scss'
})
export class SchoolDetailComponent implements OnInit {
  constructor(
    private router: Router,
    private schoolService: SchoolService,
    private toast: ToastService
  ) { }

  ngOnInit() {
  }

  editSchool(id: any): void{
    this.router.navigate(['/school/update', id]);
  }

  deleteSchool(id: any): void{
    this.schoolService.deleteSchool(id).subscribe({
      next: (response: any) => {
        this.toast.showSuccess("School deleted successfully.");
        this.router.navigate(['/school']);
      },
      error: (error: any) => {
        console.log(error.error.message);
      }
    })
  }

}
