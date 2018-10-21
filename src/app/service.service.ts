import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Service } from './service';

@Injectable({
  providedIn: 'root'
})
export class ServiceService {

  private servicesUrl = 'api/services';

  constructor(private httpClient: HttpClient) { }

  getServices(): Observable<Service[]> {
    return this.httpClient.get<Service[]>(this.servicesUrl);
  }
}
