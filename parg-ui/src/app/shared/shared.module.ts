import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedRoutingModule } from './shared-routing.module';
import { FirstWordPipe } from './pipes/first-word.pipe';
import { InchesToFeetPipe } from './pipes/inches-to-feet.pipe';
import { ExtractClassNumberPipe } from './pipes/extract-class-number.pipe';


@NgModule({
  declarations: [
    FirstWordPipe,
    InchesToFeetPipe,
    ExtractClassNumberPipe
  ],
  imports: [
    CommonModule,
    SharedRoutingModule
  ],
  exports: [ FirstWordPipe, InchesToFeetPipe, ExtractClassNumberPipe ]
})
export class SharedModule { }
