import { Component, OnInit } from '@angular/core';
import { ProductService } from '../product.service';
import { Observable, Subject } from 'rxjs';
import { Product } from '../product';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-product-search',
  templateUrl: './product-search.component.html',
  styleUrls: ['./product-search.component.css']
})
export class ProductSearchComponent implements OnInit {

  products$: Observable<Product[]>;

  private searchTerm = new Subject<String>();

  constructor(private productService: ProductService) { }

  ngOnInit() {
    this.products$ = this.searchTerm.pipe(
      debounceTime(150),
      distinctUntilChanged(),
      switchMap((term => this.productService.search(term)))
    );
  }

  search(term: String) {
    this.searchTerm.next(term);
  }

}
