import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpResponse,HttpEventType, HttpErrorResponse } from '@angular/common/http';
//import { Observable } from 'rxjs';
import { TokenStorage } from './token.storage';
import { tap } from 'rxjs/operators';
import { Observable, throwError, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { AuthenticationService } from './auth.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor(private token: TokenStorage,private authenticationService: AuthenticationService) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {


          let authReq = request;


          let currentToken = this.token.getToken();

          if (currentToken!=null) {
              authReq = request.clone({ headers: authReq.headers.set('Authorization', 'Bearer ' + currentToken)});
              }


              return next.handle(authReq).pipe(tap(data =>{
                if(data instanceof HttpResponse){
                  if(data.body.token!=null && data.body.token=='token is expired'){
                    this.authenticationService.logout();
                  }
                  else if(data.body.token!=null){
                    this.token.setToken(data.body.token);
                }

                  if(data.body.message!=null){
                  this.token.setStatus(data.status);

                  this.token.setMessage(data.body.message);

                }



              }}),catchError(err=>{
                if(err.error.message!=null){
                this.token.setStatus(err.status);

                this.token.setMessage(err.error.message);

              }
              return throwError(err.error);
            })

            );

    }
}
