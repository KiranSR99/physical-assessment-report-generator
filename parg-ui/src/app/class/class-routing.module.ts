import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: ':classId/student',
    loadChildren: () => import('../student/student.module').then(m => m.StudentModule)
  },
  {
    path: ':classId/report-card',
    loadChildren: () => import('../report-card/report-card.module').then(m => m.ReportCardModule)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClassRoutingModule { }
