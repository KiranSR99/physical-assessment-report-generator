import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SchoolRoutingModule } from './school-routing.module';
import { ListSchoolComponent } from './list-school/list-school.component';
import { UpdateSchoolComponent } from './update-school/update-school.component';
import { AddSchoolComponent } from './add-school/add-school.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SchoolDetailComponent } from './school-detail/school-detail.component';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';


@NgModule({
  declarations: [
    ListSchoolComponent,
    UpdateSchoolComponent,
    AddSchoolComponent,
    SchoolDetailComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    SchoolRoutingModule,
    ReactiveFormsModule,
    NgbModalModule
  ]
})
export class SchoolModule { }
