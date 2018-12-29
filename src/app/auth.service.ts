import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from './user';
import { Observable, of } from 'rxjs';
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

  login(user: User): Observable<User> {
    return this.httpClient.post<User>(this.loginUrl, user, httpOptions).pipe(
      tap(data => {
        sessionStorage.setItem('token', data['token']);
        this.log('Zalogowano pomyślnie');
        // this.router.navigate(['profile']);
      }),
      catchError(err => {
        this.log(err.statusText);
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

  logout() {
    sessionStorage.clear();
    // this.router.navigate(['login']);
  }

  private log(message: string) {
    this.messageService.add(message);
  }
}
