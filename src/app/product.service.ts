import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, of } from "rxjs";
import { MessageService } from "./message.service";
import { Product } from './product';
import { catchError } from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private productsUrl = "api/products";

  constructor(private httpClient: HttpClient, private messageService: MessageService) {}

  getProducts(): Observable<Product[]> {
    return this.httpClient.get<Product[]>(this.productsUrl);
      // .pipe(
      //   catchError(this.handleError("getProducts", []))
      // );
  }

  // private log(message: string) {
  //   this.messageService.add(`HeroService: ${message}`);
  // }

  // private handleError<T> (operation = 'operation', result?: T) {
  //   return (error: any): Observable<T> => {
   
  //     // TODO: send the error to remote logging infrastructure
  //     console.error(error); // log to console instead
   
  //     // TODO: better job of transforming error for user consumption
  //     this.log(`${operation} failed: ${error.message}`);
   
  //     // Let the app keep running by returning an empty result.
  //     return of(result as T);
  //   };
  // }
}
