import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AcademicYearRoutingModule } from './academic-year-routing.module';
import { AddYearComponent } from './add-year/add-year.component';
import { EditYearComponent } from './edit-year/edit-year.component';
import { ListYearComponent } from './list-year/list-year.component';


@NgModule({
  declarations: [
    AddYearComponent,
    EditYearComponent,
    ListYearComponent
  ],
  imports: [
    CommonModule,
    AcademicYearRoutingModule
  ]
})
export class AcademicYearModule { }
