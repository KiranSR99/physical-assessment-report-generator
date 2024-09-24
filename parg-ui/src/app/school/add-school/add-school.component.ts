import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastService } from '../../services/toast-service.service';
import { Router } from '@angular/router';
import { SchoolService } from '../services/school.service';

@Component({
  selector: 'app-add-school',
  templateUrl: './add-school.component.html',
  styleUrls: ['./add-school.component.scss']
})
export class AddSchoolComponent implements OnInit {

  schoolForm: FormGroup = new FormGroup({});

  imageURL: string = '';
  submitted: boolean = false;
  imageTypeError: boolean = false;
  isPresentFile: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private toast: ToastService,
    private router: Router,
    private schoolService: SchoolService
  ) { }

  ngOnInit() {
    this.initForm();
  }

  // Initializing form
  initForm(): void {
    this.schoolForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      address: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.pattern(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/)]],
      phone: ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      logo: ['', [Validators.required]]
    });
  }

  // Getter for form controls
  get schoolFormControls(): { [key: string]: AbstractControl } {
    return this.schoolForm.controls;
  }

  // Image change handler
  onFileSelected(event: any) {
    this.isPresentFile = true;
    const file: File = event.target.files[0];
    if (file) {
      if (!['image/jpeg', 'image/jpg', 'image/png'].includes(file.type) || file.size > 5242880) {
        this.imageTypeError = true;
        return;
      }

      this.imageTypeError = false;
      this.schoolForm.patchValue({ logo: file });

      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.imageURL = reader.result as string;
      };
    }
  }

  // Submit handler
  onSubmit(): void {
    this.submitted = true;

    if (this.schoolForm.valid) {
      const formData = new FormData();
      Object.keys(this.schoolForm.value).forEach((key) => {
        formData.append(key, this.schoolForm.value[key]);
      });

      this.schoolService.saveSchool(formData).subscribe({
        next: (response: any) => {
          this.toast.showSuccess("School added successfully");
          this.router.navigate(['/school']);
          this.submitted = false;
          this.imageURL = '';
          this.isPresentFile = false;
        }
      });

    } else {
      this.schoolForm.markAllAsTouched();
    }
  }

  // Reset form
  resetForm(): void {
    this.schoolForm.reset();
    this.imageURL = '';
    this.isPresentFile = false;
  }
}
