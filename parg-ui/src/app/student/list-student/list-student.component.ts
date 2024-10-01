import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ColDef, GridApi } from 'ag-grid-community';
import { AgGridAngular } from 'ag-grid-angular';
import { StudentService } from '../services/student.service';
import { SaveCompleteService } from '../../services/save-complete.service';
import { ClassService } from '../../class/services/class.service';
import { GameService } from '../../game/services/game.service';
import { ToastService } from '../../services/toast-service.service';
import { NgxUiLoaderService } from 'ngx-ui-loader';

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
  selectedStudentIds: number[] = [];

  defaultColDef = {
    flex: 1,
    minWidth: 100,
    editable: true,
  };

  public gridOptions: any = {
    selection: {
      mode: 'multiRow'
    }
  };

  constructor(
    private activatedRoute: ActivatedRoute,
    private studentService: StudentService,
    private classService: ClassService,
    private saveCompleteService: SaveCompleteService,
    private gameService: GameService,
    private toastService: ToastService,
    private router: Router,
    private loader: NgxUiLoaderService
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
        // Ensure newStudentData is cleared when refreshing data
        this.newStudentData = [];
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
      {
        field: "dateOfBirth",
        headerName: "DOB",
        valueSetter: (params: any) => {
          params.data.dateOfBirth = params.newValue;
          this.calculateAge(params.data);
          return true;
        }
      },
      { field: "age", headerName: "Age", editable: false },
      {
        field: "gender",
        headerName: "Gender",
        cellEditor: 'agSelectCellEditor',
        cellEditorParams: {
          values: ['Male', 'Female']
        },
        valueSetter: (params: any) => {
          params.data.gender = params.newValue;
          this.checkAndGenerateBMIDetails(params.data);
          return true;
        }
      },
      {
        field: "height",
        headerName: "Height",
        valueSetter: (params: any) => {
          params.data.height = params.newValue;
          this.checkAndGenerateBMIDetails(params.data);
          return true;
        }
      },
      {
        field: "weight",
        headerName: "Weight",
        valueSetter: (params: any) => {
          params.data.weight = params.newValue;
          this.checkAndGenerateBMIDetails(params.data);
          return true;
        }
      },
      { field: "bmi", headerName: "BMI", editable: false },
      { field: "bmiLevel", headerName: "BMI Level", editable: false },
      { field: "percentile", headerName: "Percentile", editable: false },
      { field: "comment", headerName: "Comment", editable: false }
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

    // Collect all data from the grid
    gridApi.forEachNode((node) => {
      allRowData.push(node.data);
    });

    // Separate new students and existing students
    const newStudents = allRowData.filter(student => student.isNew);
    const existingStudents = allRowData.filter(student => !student.isNew);

    // Prepare data for saving new students
    const studentsToSave = newStudents.map(student => ({
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

    // Prepare data for updating existing students
    const studentsToUpdate = existingStudents.map(student => ({
      studentId: student.studentId,  // Use studentId from the fetched data
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

    // Call API to save new students
    if (studentsToSave.length > 0) {
      this.saveCompleteService.saveAllDetails(studentsToSave).subscribe({
        next: () => {
          this.toastService.showSuccess('New students saved successfully');
          this.getStudentCompleteDataByClassId(this.classId);
        },
        error: (error: any) => {
          console.error('Error saving new students:', error);
          this.toastService.showError('Failed to save new students');
        }
      });
    }

    // Call API to update existing students
    if (studentsToUpdate.length > 0) {
      this.saveCompleteService.updateAllDetails(studentsToUpdate).subscribe({
        next: () => {
          this.toastService.showSuccess('Existing students updated successfully');
          this.getStudentCompleteDataByClassId(this.classId);
        },
        error: (error: any) => {
          console.error('Error updating students:', error);
          this.toastService.showError('Failed to update students');
        }
      });
    }

    // If there are no students to save or update
    if (studentsToSave.length === 0 && studentsToUpdate.length === 0) {
      this.toastService.showInfo('No changes to save');
    }
  }


  clearNewStudents() {
    const gridApi: GridApi = this.agGrid.api;
    const rowsToRemove = this.rowData.filter(row => row.isNew);
    gridApi.applyTransaction({ remove: rowsToRemove });

    // Update rowData to remove the cleared rows
    this.rowData = this.rowData.filter(row => !row.isNew);

    // Clear the newStudentData array
    this.newStudentData = [];

    // this.toastService.showInfo('Cleared newly added students');
  }

  deleteSelectedStudents() {
    if (this.selectedStudentIds.length === 0) {
      this.toastService.showInfo('No students selected for deletion');
      return;
    }

    this.saveCompleteService.deleteAllDetails(this.selectedStudentIds).subscribe({
      next: () => {
        this.toastService.showSuccess('Selected students deleted successfully');
        this.getStudentCompleteDataByClassId(this.classId);
        this.selectedStudentIds = []; // Clear the selection
      },
      error: (error: any) => {
        console.error('Error deleting students:', error);
        this.toastService.showError('Failed to delete selected students');
      }
    });
  }

  onSelectionChanged(event: any) {
    const selectedRows = this.agGrid.api.getSelectedRows();
    this.selectedStudentIds = selectedRows
      .filter(row => !row.isNew) // Filter out newly added rows
      .map(row => row.studentId);
    console.log('Selected student IDs:', this.selectedStudentIds);
  }



  calculateAge(data: any): void {
    if (data.dateOfBirth) {
      const birthDate = new Date(data.dateOfBirth);
      const today = new Date();
      let age = today.getFullYear() - birthDate.getFullYear();
      const monthDiff = today.getMonth() - birthDate.getMonth();
      if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
        age--;
      }
      data.age = age;
      this.agGrid.api.applyTransaction({ update: [data] });
    }
  }

  checkAndGenerateBMIDetails(data: any): void {
    if (data.age && data.gender && data.height && data.weight) {
      const bmiData = {
        age: data.age,
        gender: data.gender === 'Male' ? 1 : 2,
        height: parseFloat(data.height),
        weight: parseFloat(data.weight)
      };
      this.generateBMIDetails(bmiData, data);
    }
  }

  generateBMIDetails(bmiData: any, rowData: any): void {
    this.saveCompleteService.generateBMIDetails(bmiData).subscribe({
      next: (response: any) => {
        rowData.bmi = response.data.bmi;
        rowData.percentile = response.data.percentile;
        rowData.bmiLevel = response.data.bmiLevel;
        rowData.comment = response.data.comment;
        this.agGrid.api.applyTransaction({ update: [rowData] });
      },
      error: (error: any) => {
        console.error('Error generating BMI details:', error);
        this.toastService.showError('Failed to generate BMI details');
      }
    });
  }




  goToReportCenter() {
    this.loader.start(); // Start the loader
    this.router.navigate([`/school/exam/class/${this.classId}/report-card`]).then(() => {
      this.loader.stop(); // Stop the loader after navigation
    });
  }


}
