import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { MessageService } from './message.service';
import { Product } from './product';
import { catchError, tap } from 'rxjs/operators';

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

  getProducts(): Observable<Product[]> {
    return this.httpClient.get<Product[]>(this.productsUrl);
  }

  getProduct(id: number): Observable<Product> {
    const url = `${this.productsUrl}/${id}`;
    return this.httpClient.get<Product>(url);
  }

  getProductsByCategory(id: number): Observable<Product[]> {
    const url = `${this.productsUrl}/cat/${id}`;
    return this.httpClient.get<Product[]>(url);
  }

  add(product: Product): Observable<Product> {
    return this.httpClient.post<Product>(this.productsUrl, product, httpOptions);
  }

  delete(product: Product): Observable<Product> {
    const id = product.id;
    const url = `${this.productsUrl}/${id}`;

    return this.httpClient.delete<Product>(url).pipe(
      tap(_ => console.log(`Usunięto produkt ${id}`))
    );
  }

  funkcyja() {

  }
}
