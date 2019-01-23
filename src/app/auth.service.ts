import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from './user';
import { of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { MessageService } from './message.service';
import { Router } from '@angular/router';

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
    private messageService: MessageService,
    private router: Router) { }

  login(user: User) {
    return this.httpClient.post<any>(this.loginUrl, user, httpOptions);
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

  public setUsername(name: string){
    sessionStorage.setItem('name',name);
}

  public setToken(token: string) {
    sessionStorage.setItem('token', token);
  }

  public logout() {
    sessionStorage.clear();
    this.messageService.clear();
    this.router.navigate(['login']);

  }

  private log(message: string,status: number) {
    this.messageService.add(message,status);
  }
}
