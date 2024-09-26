import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ColDef } from 'ag-grid-community';
import { StudentService } from '../services/student.service';

@Component({
  selector: 'app-list-student',
  templateUrl: './list-student.component.html',
  styleUrls: ['./list-student.component.scss']
})
export class ListStudentComponent implements OnInit {
  studentCompleteData: any[] = [];
  classId: number = 0;
  rowData: any[] = []; // Data that will be shown in the grid
  colDefs: ColDef[] = []; // Column definitions

  defaultColDef = {
    flex: 1,
    minWidth: 100,
    editable: true, // You can make fields editable if necessary
  };

  constructor(
    private activatedRoute: ActivatedRoute,
    private studentService: StudentService
  ) {}

  ngOnInit() {
    // Catching the classId from the route
    this.activatedRoute.params.subscribe((params) => {
      this.classId = params['classId'];
    });

    // Fetch the student data for the given class ID
    this.getStudentCompleteDataByClassId(this.classId);
  }

  getStudentCompleteDataByClassId(classId: any): void {
    this.studentService.getStudentCompleteDataByClassId(classId).subscribe({
      next: (response: any) => {
        this.studentCompleteData = response.data;
        this.generateTableData(this.studentCompleteData);
      }
    });
  }

  // This function will transform the student data and generate columns
  generateTableData(studentCompleteData: any[]): void {
    const gamesSet = new Set<string>(); // To store unique game names
    const transformedData: any[] = [];

    // Iterate over student data and transform it
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

      // Add games data dynamically
      student.games.forEach((game: any) => {
        studentRow[game.gameName] = game.gameValue; // Add game name as key and value as the value
        gamesSet.add(game.gameName); // Add the game name to the set to ensure unique column names
      });

      transformedData.push(studentRow);
    });

    this.rowData = transformedData;

    // Generate column definitions
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

    // Add dynamic game columns
    gamesSet.forEach((gameName) => {
      this.colDefs.push({
        field: gameName,
        headerName: gameName,
      });
    });
  }
}
