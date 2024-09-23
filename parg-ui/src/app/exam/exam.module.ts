import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ExamRoutingModule } from './exam-routing.module';
import { AddExamComponent } from './add-exam/add-exam.component';
import { EditExamComponent } from './edit-exam/edit-exam.component';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    AddExamComponent,
    EditExamComponent
  ],
  imports: [
    CommonModule,
    ExamRoutingModule,
    ReactiveFormsModule
  ]
})
export class ExamModule { }
