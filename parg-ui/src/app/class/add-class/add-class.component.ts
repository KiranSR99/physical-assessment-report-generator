import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ClassService } from '../services/class.service';
import { ToastService } from '../../services/toast-service.service';

@Component({
  selector: 'app-add-class',
  templateUrl: './add-class.component.html',
  styleUrls: ['./add-class.component.scss']
})
export class AddClassComponent implements OnInit {
  @Input() schoolId: number = 0;
  @Input() examId: number = 0;
  classForm: FormGroup = new FormGroup({});

  constructor(
    private formBuilder: FormBuilder,
    private activeModal: NgbActiveModal,
    private classService: ClassService,
    private toast: ToastService
  ) { }

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm(): void {
    this.classForm = this.formBuilder.group({
      schoolId: [this.schoolId, Validators.required],
      examId: [this.examId, Validators.required],
      classes: this.formBuilder.array([this.createClassGroup()])
    });
  }

  createClassGroup(): FormGroup {
    return this.formBuilder.group({
      name: ['', Validators.required]
    });
  }

  get classes(): FormArray {
    return this.classForm.get('classes') as FormArray;
  }

  addClass(): void {
    this.classes.push(this.createClassGroup());
  }

  removeClass(index: number): void {
    if (this.classes.length > 1) {
      this.classes.removeAt(index);
    }
  }

  closeModal(): void {
    this.activeModal.close();
  }

  onSubmit(): void {
    if (this.classForm.invalid) {
      this.classForm.markAllAsTouched();
      return;
    }

    this.classService.addClasses(this.classForm.value).subscribe({
      next: () => {
        this.toast.showSuccess('Classes added successfully.');
        this.activeModal.close('success');
      },
      error: (error: any) => {
        this.toast.showError('Failed to add classes.');
      }
    });
  }
}
