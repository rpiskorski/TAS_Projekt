import { Component, OnInit } from '@angular/core';
import { Product } from '../product';
import { Service } from '../service';
import { ProductService } from '../product.service';
import { ServiceService } from '../service.service';
import { ActivatedRoute } from '@angular/router';
import { Category } from '../category';
import { CategoryService } from '../category.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {

  category: Category;

  products: Product[];

  productCount: number;

  services: Service[];

  serviceCount: number;

  searchModel = {
    term: '',
    mode: ''
  };

  constructor(
    private route: ActivatedRoute,
    private categoryService: CategoryService,
    private productService: ProductService,
    private serviceService: ServiceService) { }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.getCategory(id);
    this.getProducts(id);
    this.getServices(id);
  }

  getCategory(id: number) {
    this.categoryService.getCategory(id).subscribe(data => {
      this.category = data['category'];
    });
  }

  getProducts(id: number) {
    this.productService.getProductsByCategory(id).subscribe(products => {
      this.productCount = products['sumOfProducts'];
      this.products = products['listOfProducts'];
    });
  }

  getServices(id: number) {
    this.serviceService.getServicesByCategory(id).subscribe(data => {
        this.serviceCount = data['sumOfServices'];
        this.services = data['listOfServices'];
    });
  }

  changePage(page: number) {
    const id = +this.route.snapshot.paramMap.get('id');

    this.productService.getProductsByCategory(id, page).subscribe(products => {
      this.productCount = products['sumOfProducts'];
      this.products = products['listOfProducts'];
    });

    this.serviceService.getServicesByCategory(id, page).subscribe(data => {
      this.serviceCount = data['sumOfServices'];
      this.services = data['listOfServices'];
    });

    return page;
  }

  onSearchSubmit() {
    this.productService.searchProductsByName(this.searchModel.term).subscribe(data => {
      this.productCount = data['sumOfProducts'];
      this.products = data['listOfProducts'];
    });

    this.serviceService.searchServices(this.searchModel.term,
      this.searchModel.mode).subscribe(data => {
    this.serviceCount = data['sumOfServices'];
    this.services = data['listOfServices'];
  });
  }
}
