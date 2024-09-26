import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { SaveCompleteService } from '../../services/save-complete.service';

@Component({
  selector: 'app-add-student',
  templateUrl: './add-student.component.html',
  styleUrl: './add-student.component.scss'
})
export class AddStudentComponent implements OnInit {

  studentsForm: FormGroup = new FormGroup({});

  constructor(
    private fb: FormBuilder,
    private saveCompleteService: SaveCompleteService
  ) {}

  ngOnInit(): void {
    this.studentsForm = this.fb.group({
      students: this.fb.array([this.createStudentForm()]),
      studentEnrollments: this.fb.group({
        classId: [0, Validators.required],
        examId: [0, Validators.required],
        studentEnrollments: this.fb.array([])
      }),
      physicalReports: this.fb.array([]),
      physicalTestPerformanceMetrics: this.fb.array([])
    });
  }

  createStudentForm(): FormGroup {
    return this.fb.group({
      name: ['', Validators.required],
      dateOfBirth: ['', Validators.required],
      age: [0, Validators.required],
      gender: ['', Validators.required]
    });
  }

  createStudentEnrollmentForm(): FormGroup {
    return this.fb.group({
      studentId: [0, Validators.required],
      rollNumber: ['', Validators.required],
      section: ['', Validators.required]
    });
  }

  createPhysicalReportForm(): FormGroup {
    return this.fb.group({
      studentEnrollmentId: [0, Validators.required],
      height: [0, Validators.required],
      weight: [0, Validators.required],
      bmi: [0, Validators.required],
      bmiLevel: ['', Validators.required],
      percentile: ['', Validators.required],
      comment: ['', Validators.required]
    });
  }

  createPhysicalTestPerformanceMetricForm(): FormGroup {
    return this.fb.group({
      physicalTestId: [0, Validators.required],
      physicalReportId: [0, Validators.required],
      value: [0, Validators.required]
    });
  }

  getStudentsArray(): FormArray {
    return this.studentsForm.get('students') as FormArray;
  }

  getStudentEnrollmentsArray(): FormArray {
    return this.studentsForm.get('studentEnrollments.studentEnrollments') as FormArray;
  }

  getPhysicalReportsArray(): FormArray {
    return this.studentsForm.get('physicalReports') as FormArray;
  }

  getPhysicalTestPerformanceMetricsArray(): FormArray {
    return this.studentsForm.get('physicalTestPerformanceMetrics') as FormArray;
  }

  addStudent() {
    this.getStudentsArray().push(this.createStudentForm());
    this.getStudentEnrollmentsArray().push(this.createStudentEnrollmentForm());
    this.getPhysicalReportsArray().push(this.createPhysicalReportForm());
    this.getPhysicalTestPerformanceMetricsArray().push(this.createPhysicalTestPerformanceMetricForm());
  }

  submitForm() {
    if (this.studentsForm.valid) {
      this.saveCompleteService.saveAllData(this.studentsForm.value).subscribe({
        next: (response: any) => {
          console.log('Data saved successfully:', response);
        },
        error: (error: any) => {
          console.error('Error saving data:', error);
        }
      });
    } else {
      console.error('Form is invalid');
    }
  }
}