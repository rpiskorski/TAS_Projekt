import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse, HttpEvent} from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { TokenStorage } from './token.storage';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({ providedIn: 'root' })
export class AuthenticationService {

    constructor(private http: HttpClient,private token: TokenStorage) { }


    login(name: string, password: string){

        return this.http.post<any>('http://localhost:8080/api/login', { name, password },httpOptions);

          }
      register(name: string, password: string){
        return this.http.post<any>('http://localhost:8080/api/register', { name, password },httpOptions);
      }


    logout() {

        this.token.logout();
    }
}
