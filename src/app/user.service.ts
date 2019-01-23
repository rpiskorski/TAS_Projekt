import { Injectable } from '@angular/core';
import { User } from './user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { tap, catchError } from 'rxjs/operators';
import { of, Observable } from 'rxjs';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = 'http://localhost:8080';

  constructor(
    private httpClient: HttpClient) {}

  register(user: User) {
    const url = `${this.url}/api/register`;

    return this.httpClient.post<User>(url, user, httpOptions);
    // .pipe(
    //   tap(_ => this.messageService.add('Dodano użytkownika')),
    //   catchError(err => {
    //     this.messageService.add(err.error.message);
    //     return of(null as User);
    //   })
    // );
  }

  getUsers(): Observable<User[]> {
    const url =  `${this.url}/api/users`;
    return this.httpClient.get<User[]>(url);
  }

  getUser(userId: number): Observable<User> {
    const url =  `${this.url}/api/users/${userId}`;
    return this.httpClient.get<User>(url);
  }

  delete(userId: number) {
    const url =  `${this.url}/api/users/${userId}`;
    return this.httpClient.delete<User>(url);
  }
}
