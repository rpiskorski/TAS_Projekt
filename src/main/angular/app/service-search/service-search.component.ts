import { Component, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Service } from '../service';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { ServiceService } from '../service.service';

@Component({
  selector: 'app-service-search',
  templateUrl: './service-search.component.html',
  styleUrls: ['./service-search.component.css']
})
export class ServiceSearchComponent implements OnInit {

  services$: Observable<Service[]>;

  private searchTerm = new Subject<String>();

  constructor(private serviceService: ServiceService) { }

  ngOnInit() {
    this.services$ = this.searchTerm.pipe(
      debounceTime(150),
      distinctUntilChanged(),
      switchMap((term: String) => this.serviceService.search(term))
    );
  }

  search(term: String) {
    this.searchTerm.next(term);
  }
}
