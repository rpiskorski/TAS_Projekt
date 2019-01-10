import { Component, OnInit } from '@angular/core';

import { ProductService } from '../product.service';
import { Product } from '../product';
import { CategoryService } from '../category.service';
import { Category } from '../category';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  categories: Category[];

  products: Product[];

  productCount: number;

  model = new Product(0, '', '', 0, 0, new Category(0, ''));

  searchModel = {
    term: '',
    mode: 'productName'
  };

  onSearchSubmit() {
    if (this.searchModel.mode === 'manufacturerName') {
      this.productService.searchProductsByManufacturer(this.searchModel.term).subscribe(data => {
        if (data.length === 0) {
          this.productCount = 0;
          this.products = [];
        } else {
          this.productCount = data['sumOfProducts'];
          this.products = data['listOfProducts'];
        }
      });
    } else {
      this.productService.searchProductsByName(this.searchModel.term).subscribe(data => {
        if (data.length === 0) {
          this.productCount = 0;
          this.products = [];
        } else {
          this.productCount = data['sumOfProducts'];
          this.products = data['listOfProducts'];
        }
      });
    }
  }

  constructor(
    private categoryService: CategoryService,
    private productService: ProductService) { }

  onFilterChange(event) {
    this.productService.getProductsSortedbyName(event.target.value)
        .subscribe(data => {
      this.productCount = data['sumOfProducts'];
      this.products = data['listOfProducts'];
    });
  }

  // onSearchSubmit(term: string) {
  //   if (!term.trim()) {
  //     return;
  //   }

  //   this.productService.getProductsByName(term).subscribe(data => {
  //     this.productCount = data['sumOfProducts'];
  //     this.products = data['listOfProducts'];
  //   });
  // }

  onSearchFilterChange(aa) {
    console.log(aa);
  }
  ngOnInit() {
    this.getProducts();
    this.getCategories();
  }

  changePage(page: number) {
    this.productService.getProducts(page).subscribe(products => {
      this.productCount = products['sumOfProducts'];
      this.products = products['listOfProducts'];
    });
    return page;
  }

  getProducts() {
    this.productService.getProducts(1).subscribe(products => {
      this.productCount = products['sumOfProducts'];
      this.products = products['listOfProducts'];
    });
  }

  getCategories() {
    this.categoryService.getCategories().subscribe(data => {
      this.categories = data['listOfCategories'];
    });
  }
}
