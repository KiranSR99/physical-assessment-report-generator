import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SchoolRoutingModule } from './school-routing.module';
import { ListSchoolComponent } from './list-school/list-school.component';
import { UpdateSchoolComponent } from './update-school/update-school.component';
import { AddSchoolComponent } from './add-school/add-school.component';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    ListSchoolComponent,
    UpdateSchoolComponent,
    AddSchoolComponent
  ],
  imports: [
    CommonModule,
    SchoolRoutingModule,
    ReactiveFormsModule
  ]
})
export class SchoolModule { }
