import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Service } from './service';

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
}
