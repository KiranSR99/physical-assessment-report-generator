import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ExamRoutingModule } from './exam-routing.module';
import { AddExamComponent } from './add-exam/add-exam.component';
import { EditExamComponent } from './edit-exam/edit-exam.component';


@NgModule({
  declarations: [
    AddExamComponent,
    EditExamComponent
  ],
  imports: [
    CommonModule,
    ExamRoutingModule
  ]
})
export class ExamModule { }
