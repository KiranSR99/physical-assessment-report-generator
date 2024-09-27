import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { ColDef, GridApi } from 'ag-grid-community';
import { AgGridAngular } from 'ag-grid-angular';
import { StudentService } from '../services/student.service';
import { SaveCompleteService } from '../../services/save-complete.service';

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
  
  studentDetailsForm: FormGroup = new FormGroup({});

  defaultColDef = {
    flex: 1,
    minWidth: 100,
    editable: true,
  };

  constructor(
    private activatedRoute: ActivatedRoute,
    private studentService: StudentService,
    private fb: FormBuilder,
    private saveCompleteService: SaveCompleteService
  ) {
    this.studentDetailsForm = this.fb.group({
      studentDetails: this.fb.array([])
    });
  }

  ngOnInit() {
    this.activatedRoute.params.subscribe((params) => {
      this.classId = params['classId'];
    });
    this.getStudentCompleteDataByClassId(this.classId);
  }

  get studentDetailsFormArray() {
    return this.studentDetailsForm.get('studentDetails') as FormArray;
  }

  createStudentDetailsForm(student: any): FormGroup {
    return this.fb.group({
      examId: [student.examId || 0],
      rollNumber: [student.rollNumber || '', Validators.required],
      name: [student.name || '', Validators.required],
      className: [student.className || '', Validators.required],
      section: [student.section || '', Validators.required],
      dateOfBirth: [student.dateOfBirth || '', Validators.required],
      age: [student.age || 0, Validators.required],
      gender: [student.gender || '', Validators.required],
      height: [student.height || 0, Validators.required],
      weight: [student.weight || 0, Validators.required],
      bmi: [student.bmi || 0],
      bmiLevel: [student.bmiLevel || ''],
      percentile: [student.percentile || ''],
      comment: [student.comment || ''],
      games: this.fb.array(
        (student.games || []).map((game: any) => this.createGameForm(game))
      )
    });
  }

  createGameForm(game: any = { gameId: 0, value: 0 }): FormGroup {
    return this.fb.group({
      gameId: [game.gameId, Validators.required],
      value: [game.value, Validators.required]
    });
  }

  getStudentCompleteDataByClassId(classId: any): void {
    this.studentService.getStudentCompleteDataByClassId(classId).subscribe({
      next: (response: any) => {
        this.studentCompleteData = response.data;
        this.populateStudentDetailsForm(this.studentCompleteData);
        this.generateTableData(this.studentCompleteData);
      }
    });
  }

  populateStudentDetailsForm(studentCompleteData: any[]): void {
    const studentsArray = this.studentDetailsFormArray;
    studentsArray.clear();

    studentCompleteData.forEach(student => {
      studentsArray.push(this.createStudentDetailsForm(student));
    });
  }

  addStudentRow() {
    const newStudent = {
      examId: 1,
      rollNumber: '',
      name: '',
      className: '',
      section: '',
      dateOfBirth: '',
      age: 0,
      gender: '',
      height: 0,
      weight: 0,
      bmi: 0,
      bmiLevel: '',
      percentile: '',
      comment: '',
      games: [
        { gameId: 102, value: 0 },
        { gameId: 103, value: 0 }
      ]
    };

    this.rowData = [...this.rowData, newStudent];
    this.studentDetailsFormArray.push(this.createStudentDetailsForm(newStudent));

    // Update the grid's data
    if (this.agGrid && this.agGrid.api) {
      this.agGrid.api.applyTransaction({ add: [newStudent] });
    }
  }

  generateTableData(studentCompleteData: any[]): void {
    const gamesSet = new Set<string>();
    const transformedData: any[] = [];

    studentCompleteData.forEach((student) => {
      const studentRow: { [key: string]: any } = {
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
        comment: student.comment
      };

      student.games.forEach((game: any) => {
        studentRow[game.gameName] = game.gameValue;
        gamesSet.add(game.gameName);
      });

      transformedData.push(studentRow);
    });

    this.rowData = transformedData;

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

    gamesSet.forEach((gameName) => {
      this.colDefs.push({
        field: gameName,
        headerName: gameName,
      });
    });
  }

  saveAllStudents() {
    const gridApi: GridApi = this.agGrid.api;
    const allRowData: any[] = [];
    gridApi.forEachNode((node) => allRowData.push(node.data));

    const gamesColumnNames = this.colDefs
      .filter(col => col.field !== 'examId' && col.field !== 'rollNumber' && col.field !== 'name' && col.field !== 'className' && col.field !== 'section' && col.field !== 'dateOfBirth' && col.field !== 'age' && col.field !== 'gender' && col.field !== 'height' && col.field !== 'weight' && col.field !== 'bmi' && col.field !== 'bmiLevel' && col.field !== 'percentile' && col.field !== 'comment')
      .map(col => col.field);

    const studentsToSave = allRowData.map(student => {
      const games = gamesColumnNames.map((gameName: any) => ({
        gameId: this.getGameIdByName(gameName), // Map the game name to the corresponding gameId
        value: parseFloat(student[gameName]) || 0  // Ensure value is a number
      }));

      return {
        examId: student.examId,
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
        games: games // Attach the correct game data
      };
    });

    console.log('Data being sent to server:', JSON.stringify(studentsToSave, null, 2));

    this.saveCompleteService.saveAllDetails(studentsToSave).subscribe({
      next: (response) => {
        console.log('Data saved successfully:', response);
      },
      error: (error) => {
        console.error('Error saving data:', error);
      }
    });
  }

  getGameIdByName(gameName: string): number {
    // Add a mapping of game names to gameIds
    const gameIdMapping: { [key: string]: number } = {
      'Flamingo Balance': 102,
      'Plate Tapping': 103,
      // Add other game names and their corresponding IDs
    };

    return gameIdMapping[gameName] || 0; // Return 0 if no mapping found
  }
}
