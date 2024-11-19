import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ReportCardTemplateComponent } from './report-card-template/report-card-template.component';

const routes: Routes = [
  {
    path: '',
    component: ReportCardTemplateComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReportCardRoutingModule { }
