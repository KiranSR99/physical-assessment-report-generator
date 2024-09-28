import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ColDef, GridApi } from 'ag-grid-community';
import { AgGridAngular } from 'ag-grid-angular';
import { StudentService } from '../services/student.service';
import { SaveCompleteService } from '../../services/save-complete.service';
import { ClassService } from '../../class/services/class.service';
import { GameService } from '../../game/services/game.service';
import { ToastService } from '../../services/toast-service.service';

@Component({
  selector: 'app-list-student',
  templateUrl: './list-student.component.html',
  styleUrls: ['./list-student.component.scss']
})
export class ListStudentComponent implements OnInit {
  @ViewChild(AgGridAngular) agGrid!: AgGridAngular;

  studentCompleteData: any[] = [];
  classId: number = 0;
  rowData: any[] = [];
  colDefs: ColDef[] = [];
  examId: number = 0;
  newStudentData: any[] = [];
  games: any[] = [];

  defaultColDef = {
    flex: 1,
    minWidth: 100,
    editable: true,
  };

  constructor(
    private activatedRoute: ActivatedRoute,
    private studentService: StudentService,
    private classService: ClassService,
    private saveCompleteService: SaveCompleteService,
    private gameService: GameService,
    private toastService: ToastService
  ) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe((params) => {
      this.classId = params['classId'];
    });
    this.getClassDetailsByClassId(this.classId);
    this.getAllGamesByClassId(this.classId);
  }

  getClassDetailsByClassId(classId: any): void {
    this.classService.getClassById(classId).subscribe({
      next: (response: any) => {
        this.examId = response.data.exam.id;
        this.getStudentCompleteDataByClassId(this.classId);
      },
      error: (error: any) => {
        console.error('Error fetching class details:', error);
        this.toastService.showError('Failed to fetch class details');
      }
    });
  }

  getAllGamesByClassId(classId: any): void {
    this.gameService.getAllGamesByClassId(classId).subscribe({
      next: (response: any) => {
        this.games = response.data;
        this.generateColumnDefs();
      },
      error: (error: any) => {
        console.error('Error fetching games:', error);
        this.toastService.showError('Failed to fetch games');
      }
    });
  }

  getStudentCompleteDataByClassId(classId: any): void {
    this.saveCompleteService.getStudentCompleteDataByClassId(classId).subscribe({
      next: (response: any) => {
        this.studentCompleteData = response.data;
        this.generateTableData(this.studentCompleteData);
      },
      error: (error: any) => {
        console.error('Error fetching student data:', error);
        this.toastService.showError('Failed to fetch student data');
      }
    });
  }

  generateColumnDefs(): void {
    this.colDefs = [
      { field: "rollNumber", headerName: "Roll" },
      { field: "name", headerName: "Name" },
      { field: "className", headerName: "Class" },
      { field: "section", headerName: "Section" },
      { field: "dateOfBirth", headerName: "DOB" },
      { field: "age", headerName: "Age" },
      { field: "gender", headerName: "Gender" },
      { field: "height", headerName: "Height" },
      { field: "weight", headerName: "Weight" },
      { field: "bmi", headerName: "BMI" },
      { field: "bmiLevel", headerName: "BMI Level" },
      { field: "percentile", headerName: "Percentile" },
      { field: "comment", headerName: "Comment" }
    ];
  
    this.games.forEach((game: any) => {
      this.colDefs.push({
        field: game.physicalTestName,
        headerName: game.physicalTestName,
        valueGetter: (params: any) => {
          const gameData = params.data.games.find((g: any) => g.gameId === game.physicalTestId);
          return gameData ? gameData.value : '';  // Return the game value if it exists
        },
        valueSetter: (params: any) => {
          console.log('Setting value for:', params);  // Log the params to check the values
          const gameIndex = params.data.games.findIndex((g: any) => g.gameId === game.physicalTestId);
          if (gameIndex > -1) {
            params.data.games[gameIndex].value = params.newValue;
          } else {
            params.data.games.push({ gameId: game.physicalTestId, value: params.newValue });
          }
          return true;
        }
        
      });
    });
  }
  

  generateTableData(studentCompleteData: any[]): void {
    this.rowData = studentCompleteData.map(student => ({
      ...student,
      games: student.games.map((game: any) => ({
        gameId: this.getGameIdByName(game.gameName),  // Get gameId by gameName
        value: game.gameValue  // Keep the gameValue
      }))
    }));
  }

  getGameIdByName(gameName: string): number | undefined {
    const game = this.games.find(g => g.physicalTestName === gameName);
    return game ? game.physicalTestId : undefined;
  }

  addStudentRow() {
    const newStudent = {
      examId: this.examId,
      classId: this.classId,
      rollNumber: '',
      name: '',
      className: '',
      section: '',
      dateOfBirth: '',
      age: null,
      gender: '',
      height: null,
      weight: null,
      bmi: null,
      bmiLevel: '',
      percentile: '',
      comment: '',
      games: this.games.map(game => ({
        gameId: game.physicalTestId,
        value: null
      })),
      isNew: true
    };
  
    this.rowData = [...this.rowData, newStudent];
    this.newStudentData.push(newStudent);
  
    if (this.agGrid && this.agGrid.api) {
      this.agGrid.api.applyTransaction({ add: [newStudent] });
    }
  }  
  

  saveAllStudents() {
    const gridApi: GridApi = this.agGrid.api;
    const allRowData: any[] = [];
    
    gridApi.forEachNode((node) => {
      if (node.data.isNew) {
        allRowData.push(node.data);
      }
    });
  
    const studentsToSave = allRowData.map(student => ({
      examId: this.examId,
      classId: this.classId,
      rollNumber: student.rollNumber,
      name: student.name,
      className: student.className,
      section: student.section,
      dateOfBirth: student.dateOfBirth,
      age: student.age,
      gender: student.gender,
      height: student.height,
      weight: student.weight,
      bmi: student.bmi,
      bmiLevel: student.bmiLevel,
      percentile: student.percentile,
      comment: student.comment,
      games: student.games.map((game: any) => ({
        gameId: game.gameId,
        value: game.value !== null ? Number(game.value) : 0  // Send actual value or 0
      }))
    }));
  
    if (studentsToSave.length > 0) {
      this.saveCompleteService.saveAllDetails(studentsToSave).subscribe({
        next: () => {
          this.toastService.showSuccess('Students saved successfully');
          this.getStudentCompleteDataByClassId(this.classId);
          this.newStudentData = [];
        },
        error: (error: any) => {
          console.error('Error saving students:', error);
          this.toastService.showError('Failed to save students');  // Show error message only on failure
        }
      });
    } else {
      this.toastService.showInfo('No new students to save');
    }
  }
  
  
}
