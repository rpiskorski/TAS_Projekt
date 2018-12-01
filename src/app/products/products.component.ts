import { Component, OnInit } from '@angular/core';

import { ProductService } from '../product.service';
import { Product } from '../product';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  products: Product[];

  constructor(private productService: ProductService) { }

  ngOnInit() {
    this.getProducts();
  }

  changePage(page: number) {
    this.productService.getProducts(page).subscribe(products => this.products = products);
    return page;
  }

  getProducts() {
    this.productService.getProducts(1).subscribe(products => this.products = products);
  }
}
