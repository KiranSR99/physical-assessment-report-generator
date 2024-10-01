import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReportCardRoutingModule } from './report-card-routing.module';
import { ReportCardTemplateComponent } from './report-card-template/report-card-template.component';
import { FirstWordPipe } from '../shared/pipes/first-word.pipe';
import { AppModule } from '../app.module';
import { SharedModule } from '../shared/shared.module';
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';


@NgModule({
  declarations: [
    ReportCardTemplateComponent
  ],
  imports: [
    CommonModule,
    ReportCardRoutingModule,
    SharedModule,
    NgbTooltipModule
  ],
})
export class ReportCardModule { }
