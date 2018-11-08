import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Service } from './service';
import { tap } from 'rxjs/operators';
import { Product } from './product';

@Injectable({
  providedIn: 'root'
})
export class ServiceService {

  private servicesUrl = 'http://localhost:8080/api/services';

  constructor(private httpClient: HttpClient) { }

  getServices(): Observable<Service[]> {
    return this.httpClient.get<Service[]>(this.servicesUrl);
  }

  getService(id: number): Observable<Service> {
    const url = `${this.servicesUrl}/${id}`;
    return this.httpClient.get<Service>(url);
  }

  getServicesByCategory(id: number): Observable<Service[]> {
    const url = `${this.servicesUrl}/cat/${id}`;
    return this.httpClient.get<Service[]>(url);
  }

  add(service: Service): Observable<Service> {
    return this.httpClient.post<Service>(this.servicesUrl, service);
  }

  delete(product: Service): Observable<Service> {
    const id = product.id;
    const url = `${this.servicesUrl}/${id}`;

    return this.httpClient.delete<Service>(url).pipe(
      tap(_ => console.log(`Usunięto produkt ${id}`))
    );
  }

  search(term: String): Observable<Service[]> {
    if (!term.trim()) {
      return of([]);
    }

    return this.httpClient.get<Service[]>(`${this.servicesUrl}/name/${term}`).pipe(
      tap(_ => console.log(`Znaleziono ${term}`))
    );
  }
}
