import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from './user';
import { of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { MessageService } from './message.service';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loginUrl = 'http://localhost:8080/api/login';

  constructor(
    private httpClient: HttpClient,
    private messageService: MessageService) { }

  login(user: User) {
    return this.httpClient.post(this.loginUrl, user, httpOptions).pipe(
      tap(data => {
        sessionStorage.setItem('name', user.name);
        sessionStorage.setItem('token', data['token']);
      }),
      catchError(err => {
        this.log(err);
        return of(err as User);
      })
    );
  }

  public getToken(): string {
    return sessionStorage.getItem('token');
  }

  public isAuthenticated(): boolean {
    return this.getToken() !== null;
  }

  public getUsername(): string {
    return sessionStorage.getItem('name');
  }

  public setToken(token: string) {
    sessionStorage.setItem('token', token);
  }

  public logout() {
    sessionStorage.clear();
  }

  private log(message: string) {
    this.messageService.add(message);
  }
}
