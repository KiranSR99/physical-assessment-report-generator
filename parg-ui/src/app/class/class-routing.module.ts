import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GroupStudentComponent } from '../student/group-student/group-student.component';

const routes: Routes = [
  {
    path: ':classId/student',
    loadChildren: () => import('../student/student.module').then(m => m.StudentModule)
  },
  {
    path: ':classId/report-card',
    loadChildren: () => import('../report-card/report-card.module').then(m => m.ReportCardModule)
  },
  {
    path: ':classId/clusters',
    component: GroupStudentComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClassRoutingModule { }
