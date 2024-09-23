import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastService } from '../../services/toast-service.service';
import { SchoolService } from '../services/school.service';

@Component({
  selector: 'app-update-school',
  templateUrl: './update-school.component.html',
  styleUrl: './update-school.component.scss'
})
export class UpdateSchoolComponent implements OnInit {

  schoolForm: FormGroup = new FormGroup({});

  imageURL: string = '';
  submitted: boolean = false;
  imageTypeError: boolean = false;
  isPresentFile: boolean = false;
  schoolId: number = 0;
  school: any;

  constructor(
    private formBuilder: FormBuilder,
    private toast: ToastService,
    private router: Router,
    private schoolService: SchoolService,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit() {
    //Getting school id from route
    this.activatedRoute.params.subscribe(params => {
      this.schoolId = params['id'];
    });

    this.initForm();
    this.getSchoolById();
  }

  // Initializing form
  initForm(): void {
    this.schoolForm = this.formBuilder.group({
      schoolId: ['', [Validators.required]],
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

  getSchoolById(): void {
    this.schoolService.getSchoolById(this.schoolId).subscribe({
      next: (response: any) => {
        this.schoolForm.patchValue({
          schoolId: response.data.id,
          name: response.data.name,
          address: response.data.address,
          email: response.data.email,
          phone: response.data.phone
        });
        this.imageURL = response.data.logo;
      }
    });
  }

  // Submit handler
  onSubmit(): void {
    this.submitted = true;

    if (this.schoolForm.valid) {
      const formData = new FormData();
      Object.keys(this.schoolForm.value).forEach((key) => {
        formData.append(key, this.schoolForm.value[key]);
      });

      this.schoolService.updateSchool(formData).subscribe({
        next: (response: any) => {
          this.toast.showSuccess("School updated successfully.");
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
