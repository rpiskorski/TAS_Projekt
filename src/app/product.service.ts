import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { MessageService } from './message.service';
import { Product } from './product';
import { catchError, tap } from 'rxjs/operators';
import { ProductUser } from './product-user';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

// TODO Łapanie błędów
@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private productsUrl = 'http://localhost:8080/api/products';

  constructor(
    private httpClient: HttpClient,
    private messageService: MessageService) {}

  getProducts(id: number): Observable<Product[]> {
    const url = `${this.productsUrl}?page=${id}`;
    return this.httpClient.get<Product[]>(url);
  }

  getProduct(id: number): Observable<Product> {
    const url = `${this.productsUrl}/${id}`;
    return this.httpClient.get<Product>(url).pipe(
      catchError(err => {
        this.log(err.error.text);
        return of(null as Product);
      })
    );
  }

  getProductsByCategory(id: number, page = 1): Observable<Product[]> {
    const url = `${this.productsUrl}/cat/${id}?page=${page}`;
    return this.httpClient.get<Product[]>(url);
  }

  getProductComments(id: number): Observable<Comment[]> {
    const url = `${this.productsUrl}/${id}/comments`;
    return this.httpClient.get<Comment[]>(url).pipe(
      catchError(err => {
        this.log(err.error.text);
        return of([] as Comment[]);
      })
    );
  }

  add(product: Product): Observable<Product> {
    return this.httpClient.post<Product>(this.productsUrl, product, httpOptions).pipe(
      // tap(_ => this.messageService.add('Sukces')),
      catchError(err => {
        this.log(err.error.text);
        return of(null as Product);
      }));
  }

  delete(product: Product): Observable<Product> {
    const id = product.id;
    const url = `${this.productsUrl}/${id}`;

    return this.httpClient.delete<Product>(url).pipe(
      catchError(err => {
        this.log(err.error.text);
        return of(null as Product);
      })
    );
  }

  search(term: String): Observable<Product[]> {
    if (!term.trim()) {
      return of([]);
    }

    return this.httpClient.get<Product[]>(`${this.productsUrl}/name/${term}`);
  }

  searchInCategory(term: String, category: number): Observable<Product[]> {
    if (!term.trim()) {
      return of([]);
    }

    return this.httpClient.get<Product[]>(`${this.productsUrl}/sort/${category}?type=name&order=asc`);
  }

  private handleError<T>(result?: T) {
    return (error: any) => {
      return of(result as T);
    };
  }

  private log(message: string) {
    this.messageService.add(message);
  }
}
