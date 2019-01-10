import { Component, OnInit } from '@angular/core';
import { Product } from '../product';
import { Observable, Subject } from 'rxjs';
import { Service } from '../service';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { ProductService } from '../product.service';
import { ServiceService } from '../service.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-item-search',
  templateUrl: './item-search.component.html',
  styleUrls: ['./item-search.component.css']
})
export class ItemSearchComponent implements OnInit {

  products$: Observable<Product[]>;
  services$: Observable<Service[]>;

  private searchTerm = new Subject<String>();

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private serviceService: ServiceService) {}

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');

    this.products$ = this.searchTerm.pipe(
      debounceTime(150),
      distinctUntilChanged(),
      switchMap((term: String) => this.productService.searchInCategory(term, id))
    );

    // this.services$ = this.searchTerm.pipe(
    //   debounceTime(150),
    //   distinctUntilChanged(),
    //   switchMap((term: String) => this.serviceService.searchInCategory(term, id))
    // );
  }

  search(term: String) {
    this.searchTerm.next(term);
  }

}
