import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { GameService } from '../services/game.service';
import { ToastService } from '../../services/toast-service.service';

@Component({
  selector: 'app-list-games',
  templateUrl: './list-games.component.html',
  styleUrls: ['./list-games.component.scss']
})
export class ListGamesComponent implements OnInit {
  gameForm: FormGroup;
  games: any[] = [];
  isEditing = false;
  selectedGameId: number | null = null;

  constructor(
    private gameService: GameService,
    private toast: ToastService,
    private fb: FormBuilder
  ) {
    this.gameForm = this.fb.group({
      physicalTestName: ['', [Validators.required]],
      physicalTestDescription: ['', [Validators.required]],
      physicalTestUnit: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.loadGames();
  }

  loadGames(): void {
    this.gameService.getAllGames().subscribe({
      next: (games) => {
        this.games = games.data.content;
      },
      error: (error) => {
        this.toast.showError('Failed to load games');
        console.error('Error loading games:', error);
      }
    });
  }

  onSubmit(): void {
    if (this.gameForm.valid) {
      if (this.isEditing) {
        this.updateGame();
      } else {
        this.addGame();
      }
    } else {
      this.gameForm.markAllAsTouched();
    }
  }

  addGame(): void {
    this.gameService.saveGame(this.gameForm.value).subscribe({
      next: () => {
        this.toast.showSuccess('Game added successfully');
        this.loadGames();
        this.resetForm();
      },
      error: (error) => {
        this.toast.showError('Failed to add game');
        console.error('Error adding game:', error);
      }
    });
  }

  updateGame(): void {
    if (this.selectedGameId) {
      this.gameService.updateGame(this.selectedGameId, this.gameForm.value).subscribe({
        next: () => {
          this.toast.showSuccess('Game updated successfully');
          this.loadGames();
          this.resetForm();
        },
        error: (error) => {
          this.toast.showError('Failed to update game');
          console.error('Error updating game:', error);
        }
      });
    }
  }

  editGame(game: any): void {
    this.isEditing = true;
    this.selectedGameId = game.physicalTestId;
    this.gameForm.patchValue({
      physicalTestName: game.physicalTestName,
      physicalTestDescription: game.physicalTestDescription,
      physicalTestUnit: game.physicalTestUnit
    });
  }

  deleteGame(id: number): void {
    if (confirm('Are you sure you want to delete this game?')) {
      this.gameService.deleteGame(id).subscribe({
        next: () => {
          this.toast.showSuccess('Game deleted successfully');
          this.loadGames();
        },
        error: (error) => {
          this.toast.showError('Failed to delete game');
          console.error('Error deleting game:', error);
        }
      });
    }
  }

  resetForm(): void {
    this.gameForm.reset();
    this.isEditing = false;
    this.selectedGameId = null;
  }
}