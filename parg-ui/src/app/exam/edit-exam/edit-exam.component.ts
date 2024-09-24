import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastService } from '../../services/toast-service.service';
import { ExamService } from '../services/exam.service';

@Component({
  selector: 'app-edit-exam',
  templateUrl: './edit-exam.component.html',
  styleUrl: './edit-exam.component.scss'
})
export class EditExamComponent implements OnInit {
  @Input() examId: any;
  examForm: FormGroup = new FormGroup({});
  submitted = false;

  constructor(
    private activeModal: NgbActiveModal,
    private formBuilder: FormBuilder,
    private examService: ExamService,
    private toast: ToastService
  ) { }

  ngOnInit(): void {
    this.initializeForm();
    this.loadExamDetails(this.examId);
  }

  initializeForm(): void {
    this.examForm = this.formBuilder.group({
      year: ['', [Validators.required, Validators.pattern(/^(20\d{2})$/)]],
      examName: ['', Validators.required]
    });
  }

  // Load the exam details based on examId
  loadExamDetails(examId: any): void {
    this.examService.getExamById(examId).subscribe({
      next: (response: any) => {
        this.examForm.patchValue(response.data);
      },
      error: (error: any) => {
        console.log(error.error.message);
      }
    });
  }

  modalClose(): void {
    this.activeModal.close();
  }

  onClear(): void {
    this.examForm.reset();
    this.submitted = false;
  }

  onSubmit(): void {
    this.submitted = true;

    if (this.examForm.invalid) {
      this.examForm.markAllAsTouched();
      return;
    }

    const examData = {
      year: this.examForm.value.year,
      examName: this.examForm.value.examName
    };

    this.examService.updateExam(this.examId, this.examForm.value).subscribe({
      next: (response: any) => {
        this.toast.showSuccess(response.message);
        this.activeModal.close('success');
      },
      error: (error: any) => {
        this.toast.showError(error.error.message);
      }
    });
  }

}
