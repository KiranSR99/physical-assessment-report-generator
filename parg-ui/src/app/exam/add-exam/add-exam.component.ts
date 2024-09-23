import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastService } from '../../services/toast-service.service';
import { ExamService } from '../services/exam.service';

@Component({
  selector: 'app-add-exam',
  templateUrl: './add-exam.component.html',
  styleUrls: ['./add-exam.component.scss']
})
export class AddExamComponent implements OnInit {
  examForm: FormGroup = new FormGroup({});
  submitted = false;

  // EventEmitter to notify parent component about successful addition
  @Output() examAdded = new EventEmitter<void>();

  constructor(
    private activateModal: NgbActiveModal,
    private formBuilder: FormBuilder,
    private examService: ExamService,
    private toast: ToastService
  ) { }

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm(): void {
    this.examForm = this.formBuilder.group({
      year: ['', [Validators.required, Validators.pattern(/^(20\d{2})$/)]],
      examName: ['', Validators.required]
    });
  }

  modalClose(): void {
    this.activateModal.close();
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

    this.examService.addExam(this.examForm.value).subscribe({
      next: (response) => {
        this.toast.showSuccess(response.message);
        this.examAdded.emit();
        this.modalClose();
      },
      error: (error) => {
        this.toast.showError(error.error.message);
      }
    });
  }
}
