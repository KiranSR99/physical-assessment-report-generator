import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SaveCompleteService } from '../../services/save-complete.service';
import { ToastService } from '../../services/toast-service.service';
import { ClassService } from '../../class/services/class.service';
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';
import { NgxUiLoaderService } from 'ngx-ui-loader';

@Component({
  selector: 'app-report-card-template',
  templateUrl: './report-card-template.component.html',
  styleUrl: './report-card-template.component.scss'
})
export class ReportCardTemplateComponent implements OnInit {
  classId: number = 0;
  studentId: number = 0;
  studentDetails: any[] = [];
  schoolDetails: any = {};
  examDetails: any = {};
  classDetails: any = {};
  gameDetails: any = {};

  constructor(
    private activatedRoute: ActivatedRoute,
    private saveCompleteService: SaveCompleteService,
    private classService: ClassService,
    private toast: ToastService,
    private router: Router,
    private loader: NgxUiLoaderService
  ) { }

  ngOnInit(): void {
    //Getting required Ids....
    this.classId = this.activatedRoute.snapshot.params['classId'];

    this.getStudentCompleteDataByClassId(this.classId);
    this.getClassDetailsByClassId(this.classId);
  }

  getClassDetailsByClassId(classId: number): void {
    this.classService.getClassById(classId).subscribe({
      next: (response: any) => {
        this.classDetails = response.data;

        // Assign values after data is fetched
        this.schoolDetails = this.classDetails.school;
        this.examDetails = this.classDetails.exam;
        this.gameDetails = this.classDetails.physicalTests;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  getStudentCompleteDataByClassId(classId: number): void {
    this.saveCompleteService.getStudentCompleteDataByClassId(classId).subscribe({
      next: (response: any) => {
        this.studentDetails = response.data;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  getStudentGameValue(student: any, name: string): number | string {
    const game = student.games.find((g: any) => g.gameName === name);
    return game ? game.gameValue : 'N/A';
  }

  async exportReportCards(): Promise<void> {
    this.loader.start(); // Start the loader

    try {
      const reportCards = document.querySelectorAll('.wrapper');
      const pdf = new jsPDF({
        orientation: 'portrait',
        unit: 'mm',
        format: 'a4',
        compress: true
      });

      const scale = 3;
      const pdfWidth = pdf.internal.pageSize.getWidth();
      const pdfHeight = pdf.internal.pageSize.getHeight();

      for (let i = 0; i < reportCards.length; i++) {
        const card = reportCards[i] as HTMLElement;

        await this.loadImages(card);

        const canvas = await html2canvas(card, {
          scale: scale,
          useCORS: true,
          allowTaint: true,
          logging: false,
          windowWidth: card.scrollWidth,
          windowHeight: card.scrollHeight
        });

        const imgData = canvas.toDataURL('image/jpeg', 1.0);

        if (i > 0) {
          pdf.addPage();
        }

        pdf.addImage(imgData, 'JPEG', 0, 0, pdfWidth, pdfHeight, '', 'FAST');
      }

      pdf.save(this.classDetails.name + '.pdf');
      this.toast.showSuccess('Report cards exported successfully!');
    } catch (error) {
      console.error('Error exporting report cards:', error);
      this.toast.showError('Failed to export report cards.');
    } finally {
      this.loader.stop(); // Stop the loader after PDF generation is done or if an error occurs
    }
  }


  private async loadImages(element: HTMLElement): Promise<void> {
    const images = element.getElementsByTagName('img');
    const imagePromises = Array.from(images).map(img => {
      if (img.complete) {
        return Promise.resolve();
      } else {
        return new Promise((resolve) => {
          img.onload = img.onerror = resolve;
        });
      }
    });

    await Promise.all(imagePromises);
  }




  startLoader(): void {
    this.loader.start();
  }

}
