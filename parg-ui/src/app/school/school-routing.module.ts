import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListSchoolComponent } from './list-school/list-school.component';
import { AddSchoolComponent } from './add-school/add-school.component';
import { UpdateSchoolComponent } from './update-school/update-school.component';
import { SchoolDetailComponent } from './school-detail/school-detail.component';

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
    path: 'school-detail/:id',
    component: SchoolDetailComponent
  },
  {
    path: 'exam',
    loadChildren: () => import('../exam/exam.module').then(m => m.ExamModule)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SchoolRoutingModule { }
