import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StudentRoutingModule } from './student-routing.module';
import { AddStudentComponent } from './add-student/add-student.component';
import { ListStudentComponent } from './list-student/list-student.component';
import { EditStudentComponent } from './edit-student/edit-student.component';
import { ReactiveFormsModule } from '@angular/forms';
import { AgGridAngular } from 'ag-grid-angular';
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { ImportStudentsComponent } from './import-students/import-students.component';
import { GroupStudentComponent } from './group-student/group-student.component';


@NgModule({
  declarations: [
    AddStudentComponent,
    ListStudentComponent,
    EditStudentComponent,
    ImportStudentsComponent,
    GroupStudentComponent
  ],
  imports: [
    CommonModule,
    StudentRoutingModule,
    ReactiveFormsModule,
    AgGridAngular,
    NgbTooltipModule
  ]
})
export class StudentModule { }
