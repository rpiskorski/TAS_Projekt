import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpUserEvent, HttpResponse } from '@angular/common/http';
import { AuthService } from './auth.service';
import { Observable, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';


@Injectable()
export class TokenInterceptor implements HttpInterceptor {

    constructor(private authService: AuthService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler)
            : Observable<HttpEvent<any>> {

        const token = this.authService.getToken();

        if (token !== null) {
            req = req.clone({
                setHeaders: {
                    'Authorization': `Bearer ${token}`
                }
            });
        }

        return next.handle(req).pipe(
            tap(data => {
                if (data instanceof HttpResponse) {
                    if (data.body.token !== null && data.body.token === 'token is expired') {
                        this.authService.logout();
                    } else if (data.body.token !== null) {
                        this.authService.setToken(data.body.token);
                    }
                }
            }));
            // catchError(err => {
            //     return throwError(err);
            // }));
    }
}
