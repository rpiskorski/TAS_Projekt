import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { MessageService } from './message.service';
import { Product } from './product';
import { catchError, tap } from 'rxjs/operators';
import { Comment } from './comment';

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

  getProductsSortedbyName(params: string) {
    let url = '';

    switch (params) {
      case 'nameAsc': url = 'type=name&order=asc'; break;
      case 'nameDesc': url = 'type=name&order=desc'; break;
      case 'ratingAsc': url = 'type=rating&order=asc'; break;
      default: url = 'type=rating&order=desc'; break;
    }

    return this.httpClient.get<Product[]>(`${this.productsUrl}/sort?${url}`);
  }

  getProductsByName(term: string) {
    return this.httpClient.get<Product[]>(`${this.productsUrl}/name/${term}`);
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

  addComment(comment: Comment, id: number) {
    const url = `${this.productsUrl}/${id}/comments`;
    return this.httpClient.post<Comment>(url,
      { 'comment': comment.comment,
        'rating': comment.rating
      },
      httpOptions);
  }

  delete(product: Product): Observable<Product> {
    const id = product.id;
    const url = `${this.productsUrl}/${id}`;

    return this.httpClient.delete<Product>(url).pipe(
      tap(x => {
        console.log(x);
      }),
      catchError(err => {
        this.log(err.error.text);
        return of(null as Product);
      })
    );
  }

  deleteComment(comment: Comment, productId: number) {
    const url = `${this.productsUrl}/${productId}/comments/${comment.id}`;

    return this.httpClient.delete<Comment>(url, httpOptions).pipe(
      catchError(err => {
        console.log(err);
        return of(null);
      })
    );

  }

  searchProductsByName(term: string): Observable<Product[]> {
    return this.httpClient.get<Product[]>(`${this.productsUrl}/name/${term}`, httpOptions).pipe(
      catchError(err => {
        return of([] as Product[]);
      })
    );
  }

  searchProductsByManufacturer(term: string): Observable<Product[]> {
    return this.httpClient.get<Product[]>(`${this.productsUrl}/manufacturer/${term}`, httpOptions).pipe(
      catchError(err => {
        return of([] as Product[]);
      })
    );
  }

  search(term: String): Observable<Product[]> {
    if (!term.trim()) {
      return of([]);
    }

    // const data = this.httpClient.get<Product[]>(`${this.productsUrl}/name/${term}`, httpOptions);
    // console.log(data);
    return this.httpClient.get<Product[]>(`${this.productsUrl}/name/${term}`, httpOptions);
  }


  searchInCategory(term: String, category: number): Observable<Product[]> {
    if (!term.trim()) {
      return of([]);
    }

    return this.httpClient.get<Product[]>(`${this.productsUrl}/sort/${category}?type=name&order=asc`);
  }


  private log(message: string) {
    this.messageService.add(message);
  }
}
