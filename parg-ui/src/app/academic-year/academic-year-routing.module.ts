import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListYearComponent } from './list-year/list-year.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: ''
  },
  {
    path: '',
    component: ListYearComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AcademicYearRoutingModule { }
