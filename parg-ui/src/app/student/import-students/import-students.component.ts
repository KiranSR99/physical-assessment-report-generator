import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { SaveCompleteService } from '../../services/save-complete.service';
import { ToastService } from '../../services/toast-service.service';

@Component({
  selector: 'app-import-students',
  templateUrl: './import-students.component.html',
  styleUrl: './import-students.component.scss'
})
export class ImportStudentsComponent implements OnInit {
  @Input() examId: number = 0;
  file: File | null = null;

  constructor(
    private activeModal: NgbActiveModal,
    private saveCompleteService: SaveCompleteService,
    private toast: ToastService
  ) { }

  ngOnInit() {
  }

  onDragOver(event: DragEvent) {
    event.preventDefault();
    event.stopPropagation();
    const dropZone = event.target as HTMLElement;
    dropZone.classList.add('dragover');
  }

  onDragLeave(event: DragEvent) {
    event.preventDefault();
    event.stopPropagation();
    const dropZone = event.target as HTMLElement;
    dropZone.classList.remove('dragover');
  }

  onDrop(event: DragEvent) {
    event.preventDefault();
    event.stopPropagation();
    const dropZone = event.target as HTMLElement;
    dropZone.classList.remove('dragover');

    const files = event.dataTransfer?.files;
    if (files && files.length > 0) {
      this.handleFile(files[0]);
    }
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.handleFile(input.files[0]);
    }
  }

  handleFile(file: File) {
    this.file = file;
  }

  onProceed() {
    if (this.file) {
      const formData = new FormData();
      formData.append('file', this.file);

      this.saveCompleteService.saveExcelData(this.examId, formData).subscribe({
        next: () => {
          this.toast.showSuccess('Student details added to respective classes successfully.');
          this.activeModal.close('File uploaded successfully');
        },
        error: (error: any) => {
          console.error('Error uploading file', error);
        }
      });
    }
  }

  modalClose(): void {
    this.activeModal.close();
  }
}