import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SchoolService } from '../services/school.service';
import { ToastService } from '../../services/toast-service.service';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AddExamComponent } from '../../exam/add-exam/add-exam.component';
import { ExamService } from '../../exam/services/exam.service';
import { EditExamComponent } from '../../exam/edit-exam/edit-exam.component';
import { AddClassComponent } from '../../class/add-class/add-class.component';
import { ClassService } from '../../class/services/class.service';

@Component({
  selector: 'app-school-detail',
  templateUrl: './school-detail.component.html',
  styleUrls: ['./school-detail.component.scss']
})
export class SchoolDetailComponent implements OnInit {
  schoolId: any;
  schoolDetails: any;
  exams: any;
  selectedExam: any;
  classes: any;

  constructor(
    private router: Router,
    private schoolService: SchoolService,
    private toast: ToastService,
    private activatedRoute: ActivatedRoute,
    private modalService: NgbModal,
    private examService: ExamService,
    private classService: ClassService
  ) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {
      this.schoolId = +params['id'];

      if (this.schoolId) {
        this.getSchoolDetailsById(this.schoolId);
      }
    });

    this.getAllExams();
    this.getAllExamClasses();
  }

  getSchoolDetailsById(schoolId: any): void {
    this.schoolService.getSchoolById(schoolId).subscribe({
      next: (response: any) => {
        this.schoolDetails = response.data;
      },
      error: (error: any) => {
        console.log(error.error.message);
      }
    })
  }

  editSchool(id: any): void {
    this.router.navigate(['/school/update', id]);
  }

  deleteSchool(id: any): void {
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

  // Open the modal and subscribe to the event when the exam is added
  openAddExamModal(): void {
    const modalRef: NgbModalRef = this.modalService.open(AddExamComponent, {
      centered: true,
      size: 'md',
      backdrop: 'static'
    });

    modalRef.componentInstance.examAdded.subscribe(() => {
      // Refresh the list of exams when the event is emitted
      this.getAllExams();
    });
  }

  getAllExams(): void {
    this.examService.getAllExams().subscribe({
      next: (response: any) => {
        this.exams = response.data;
      },
      error: (error: any) => {
        console.log(error.error.message);
      }
    })
  }

  editExam(examId: any): void {
    const modalRef = this.modalService.open(EditExamComponent, {
      centered: true,
      size: 'md',
      backdrop: 'static'
    });
  
    modalRef.componentInstance.examId = examId;
  
    modalRef.result.then((result: any) => {
      if (result === 'success') {
        this.getAllExams();
      }
    }).catch((error) => {
      console.log('Modal dismissed: ', error);
    });
  }

  deleteExam(id: any): void {
    this.examService.deleteExam(id).subscribe({
      next: (response: any) => {
        this.toast.showSuccess("Exam deleted successfully.");
        this.getAllExams();
      },
      error: (error: any) => {
        console.log(error.error.message);
      }
    })
  }


  selectExam(exam: any): void {
    this.selectedExam = exam;
    // this.getExamClasses(exam.id);
  }

  getAllExamClasses(): void {
    this.classService.getAllClasses().subscribe({
      next: (response: any) => {
        this.classes = response.data;
      },
      error: (error: any) => {
        console.log(error.error.message);
      }
    })
  }

  addClassToExam(): void {
    const modalRef = this.modalService.open(AddClassComponent, {
      centered: true,
      size: 'md',
      backdrop: 'static'
    });

    modalRef.componentInstance.schoolId = this.schoolId;

    modalRef.result.then((result: any) => {
      if (result === 'success') {
        this.getAllExamClasses();
      }
    }).catch((error: any) => {
      console.log('Modal dismissed: ', error);
    });
  }

}
