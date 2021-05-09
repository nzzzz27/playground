import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import {SpinnerService} from './spinner.service';

@Injectable()
export class SpinnerInterceptor implements HttpInterceptor {

  defaultTimeout: number

  started = Date.now()

  constructor(
    public spinnerService: SpinnerService
  ) { }

  intercept(
    req:  HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<boolean>> {

    const elapsed = Date.now() - this.started

    if(elapsed >= 1000) {
      this.spinnerService.show()
    }
    return next.handle(req).pipe(finalize(() => this.spinnerService.hide()))
  }
}
