import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModule, NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { LayoutModule } from './layout/layout.module';
import { provideHttpClient } from '@angular/common/http';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AgGridAngular } from 'ag-grid-angular';
import { NgxUiLoaderConfig, NgxUiLoaderHttpModule, NgxUiLoaderModule } from 'ngx-ui-loader';
import { LoaderComponent } from './components/loader/loader.component';

const ngxUiLoaderConfig: NgxUiLoaderConfig = {
  blur: 3,
  delay: 0,
  fastFadeOut: true,
  fgsColor: "#f6f7f7",
  fgsSize: 0,
  fgsType: "folding-cube",
  pbThickness: 2,
  pbColor: "#f6f7f7",
  text: "Processing... Please wait",
  textColor: "#f6f7f7",
  textPosition: "center-center",
  maxTime: -1,
  // minTime: 500,
  // logoUrl: "images/logo/ep avatar.png",
  // logoPosition: "center-center",
  // logoSize: 120,
};


@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    LoaderComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    LayoutModule,
    NgbModule,
    ToastrModule.forRoot(
      
    ),
    AgGridAngular,
    NgxUiLoaderModule.forRoot(ngxUiLoaderConfig),
    NgxUiLoaderHttpModule.forRoot({
      showForeground: true,
    }),
  ],
  providers: [
    provideHttpClient()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
