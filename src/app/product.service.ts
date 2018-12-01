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
    return this.httpClient.get<Product>(url);
  }

  getProductsByCategory(id: number): Observable<Product[]> {
    const url = `${this.productsUrl}/cat/${id}`;
    return this.httpClient.get<Product[]>(url);
  }

  getProductUsers(id: number): Observable<ProductUser[]> {
    // FIXME (Backend ma zwracać wiele komentarzy)
    const url = `${this.productsUrl}/${id}/1`;
    const x = this.httpClient.get<ProductUser[]>(url);
    // const com = [1, 2, 3];
    // console.log(x);
    return x;
  }

  add(product: Product): Observable<Product> {
    return this.httpClient.post<Product>(this.productsUrl, product, httpOptions);
  }

  delete(product: Product): Observable<Product> {
    const id = product.id;
    const url = `${this.productsUrl}/${id}`;

    return this.httpClient.delete<Product>(url);
  }

  search(term: String): Observable<Product[]> {
    if (!term.trim()) {
      return of([]);
    }

    return this.httpClient.get<Product[]>(`${this.productsUrl}/name/${term}`);
  }
}
