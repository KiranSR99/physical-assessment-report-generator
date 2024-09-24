import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ClassRoutingModule } from './class-routing.module';
import { AddClassComponent } from './add-class/add-class.component';
import { EditClassComponent } from './edit-class/edit-class.component';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    AddClassComponent,
    EditClassComponent
  ],
  imports: [
    CommonModule,
    ClassRoutingModule,
    ReactiveFormsModule
  ]
})
export class ClassModule { }
