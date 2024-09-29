import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { GameService } from '../../game/services/game.service';
import { ToastService } from '../../services/toast-service.service';
import { ClassService } from '../services/class.service';

@Component({
  selector: 'app-edit-class',
  templateUrl: './edit-class.component.html',
  styleUrl: './edit-class.component.scss'
})
export class EditClassComponent implements OnInit {
  @Input() classData: any;
  updateForm: FormGroup;
  games: any[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private activeModal: NgbActiveModal,
    private classService: ClassService,
    private toast: ToastService,
    private gameService: GameService
  ) {
    this.updateForm = this.formBuilder.group({
      name: ['', Validators.required],
      physicalTestIds: [[]]
    });
  }

  ngOnInit(): void {
    this.getAllGames();
    this.initializeForm();
  }

  initializeForm(): void {
    if (this.classData) {
      this.updateForm.patchValue({
        name: this.classData.name,
        physicalTestIds: this.classData.physicalTestIds || []
      });
    }
  }

  getAllGames(): void {
    this.gameService.getAllGames().subscribe({
      next: (response: any) => {
        this.games = response.data.content;
      },
      error: (error: any) => {
        console.error('Failed to load games:', error);
        this.toast.showError('Failed to load games.');
      }
    });
  }

  onGameCheckboxChange(event: any, gameId: number): void {
    const physicalTestIds = this.updateForm.get('physicalTestIds')?.value as number[];
    if (event.target.checked) {
      physicalTestIds.push(gameId);
    } else {
      const index = physicalTestIds.indexOf(gameId);
      if (index > -1) {
        physicalTestIds.splice(index, 1);
      }
    }
    this.updateForm.patchValue({ physicalTestIds });
  }

  isGameSelected(gameId: number): boolean {
    return this.updateForm.get('physicalTestIds')?.value.includes(gameId);
  }

  closeModal(): void {
    this.activeModal.close();
  }

  onSubmit(): void {
    if (this.updateForm.invalid) {
      this.updateForm.markAllAsTouched();
      return;
    }

    this.classService.updateClass(this.classData.id, this.updateForm.value).subscribe({
      next: () => {
        this.toast.showSuccess('Class updated successfully.');
        this.activeModal.close('success');
      },
      error: (error: any) => {
        console.error('Failed to update class:', error);
        this.toast.showError('Failed to update class.');
      }
    });
  }
}
