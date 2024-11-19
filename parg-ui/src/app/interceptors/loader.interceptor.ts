import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { NgxUiLoaderService } from 'ngx-ui-loader';

@Injectable()
export class LoaderInterceptor implements HttpInterceptor {
  constructor(private ngxService: NgxUiLoaderService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.ngxService.start(); // Start loader
    return next.handle(req).pipe(
      finalize(() => this.ngxService.stop())
    );
  }
}
