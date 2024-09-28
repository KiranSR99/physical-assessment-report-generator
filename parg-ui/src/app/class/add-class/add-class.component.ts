import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ClassService } from '../services/class.service';
import { ToastService } from '../../services/toast-service.service';
import { GameService } from '../../game/services/game.service';

@Component({
  selector: 'app-add-class',
  templateUrl: './add-class.component.html',
  styleUrls: ['./add-class.component.scss']
})
export class AddClassComponent implements OnInit {
  @Input() schoolId: number = 0;
  @Input() examId: number = 0;
  classForm: FormGroup = new FormGroup({});
  games: any[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private activeModal: NgbActiveModal,
    private classService: ClassService,
    private toast: ToastService,
    private gameService: GameService
  ) { }

  ngOnInit(): void {
    this.initializeForm();
    this.getAllGames();
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
      name: ['', Validators.required],
      physicalTestIds: this.formBuilder.array([])
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

  getAllGames(): void {
    this.gameService.getAllGames().subscribe({
      next: (response: any) => {
        this.games = response.data.content;
      },
      error: (error: any) => {
        console.log(error);
        this.toast.showError('Failed to load games.');
      }
    });
  }

  onGameCheckboxChange(event: any, classIndex: number, gameId: number): void {
    const physicalTestIds = this.classes.at(classIndex).get('physicalTestIds') as FormArray;
    if (event.target.checked) {
      physicalTestIds.push(this.formBuilder.control(gameId));
    } else {
      const index = physicalTestIds.controls.findIndex(x => x.value === gameId);
      physicalTestIds.removeAt(index);
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