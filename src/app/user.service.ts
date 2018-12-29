import { Injectable } from '@angular/core';
import { MessageService } from './message.service';
import { User } from './user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { tap, catchError } from 'rxjs/operators';
import { of } from 'rxjs';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = 'http://localhost:8080/signup';

  constructor(
    private httpClient: HttpClient,
    private messageService: MessageService) {}

  register(user: User) {
    const url = 'http://localhost:8080/api/register';

    return this.httpClient.post<User>(url, user, httpOptions).pipe(
      tap(_ => this.messageService.add('Dodano użytkownika')),
      catchError(err => {
        this.messageService.add(err.error.text);
        return of(null as User);
      })
    );
  }
}
