import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListSchoolComponent } from './list-school/list-school.component';
import { AddSchoolComponent } from './add-school/add-school.component';
import { UpdateSchoolComponent } from './update-school/update-school.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: '',
  },
  {
    path: '',
    component: ListSchoolComponent
  },
  {
    path: 'add',
    component: AddSchoolComponent
  },
  {
    path: 'update/:id',
    component: UpdateSchoolComponent
  },
  {
    path: 'year',
    loadChildren: () => import('../academic-year/academic-year.module').then( m => m.AcademicYearModule)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SchoolRoutingModule { }
