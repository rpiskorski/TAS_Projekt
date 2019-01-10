import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Service } from './service';
import { tap, catchError } from 'rxjs/operators';
import { MessageService } from './message.service';
import { Comment } from './comment';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class ServiceService {

  private servicesUrl = 'http://localhost:8080/api/services';

  constructor(
    private httpClient: HttpClient,
    private messageService: MessageService) { }

  getServices(id: number): Observable<Service[]> {
    const url = `${this.servicesUrl}?page=${id}`;
    return this.httpClient.get<Service[]>(url);
  }

  getService(id: number): Observable<Service> {
    const url = `${this.servicesUrl}/${id}`;
    return this.httpClient.get<Service>(url);
  }

  getServicesByCategory(id: number, page = 1): Observable<Service[]> {
    const url = `${this.servicesUrl}/cat/${id}?page=${page}`;
    return this.httpClient.get<Service[]>(url);
  }

  add(service: Service): Observable<Service> {
    return this.httpClient.post<Service>(this.servicesUrl, service, httpOptions).pipe(
      tap(_ => this.log('Usługa dodana!'),
      catchError(err => {
        this.log(err.error);
        return of(null as Service);
      })
    ));
  }

  delete(service: Service): Observable<Service> {
    const id = service.id;
    const url = `${this.servicesUrl}/${id}`;

    return this.httpClient.delete<Service>(url).pipe(
      tap(_ => console.log(`Usunięto usługę ${id}`))
    );
  }

  search(term: String): Observable<Service[]> {
    if (!term.trim()) {
      return of([]);
    }

    return this.httpClient.get<Service[]>(`${this.servicesUrl}/name/${term}`);
  }

  addComment(comment: Comment, id: number) {
    const url = `${this.servicesUrl}/${id}/comments`;
    return this.httpClient.post<Comment>(url,
      { 'comment': comment.comment,
        'rating': comment.rating
      },
      httpOptions);
  }

  getServiceComments(id: number): Observable<Comment[]> {
    const url = `${this.servicesUrl}/${id}/comments`;
    return this.httpClient.get<Comment[]>(url).pipe(
      catchError(err => {
        this.log(err.error.text);
        return of([] as Comment[]);
      })
    );
  }

  deleteComment(comment: Comment, serviceId: number) {
    const url = `${this.servicesUrl}/${serviceId}/comments/${comment.id}`;

    return this.httpClient.delete<Comment>(url, httpOptions).pipe(
      catchError(err => {
        console.log(err);
        return of(null);
      })
    );

  }

  getServicesSortedByName(params: string): Observable<Service[]> {
    let url = '';

    switch (params) {
      case 'nameAsc': url = 'type=name&order=asc'; break;
      case 'nameDesc': url = 'type=name&order=desc'; break;
      case 'ratingAsc': url = 'type=rating&order=asc'; break;
      default: url = 'type=rating&order=desc'; break;
    }

    return this.httpClient.get<Service[]>(`${this.servicesUrl}/sort?${url}`);
  }

  searchServices(term: string, mode: string): Observable<Service[]> {
    if (mode === 'manufacturerName') {
      return this.httpClient.get<Service[]>(`${this.servicesUrl}/manufacturer/${term}`, httpOptions);
    } else {
      return this.httpClient.get<Service[]>(`${this.servicesUrl}/name/${term}`, httpOptions);
    }
  }

  searchInCategory(term: String, category: number): Observable<Service[]> {
    if (!term.trim()) {
      return of([]);
    }

    return this.httpClient.get<Service[]>(`${this.servicesUrl}/sort/${category}?type=name&order=asc`);
  }

  private log(message: string) {
    this.messageService.add(message);
  }
}
