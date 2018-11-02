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

  services: Service[];

  // constructor() {}
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
    this.categoryService.getCategory(id).subscribe(category => this.category = category);
  }

  getProducts(id: number) {
    this.productService.getProductsByCategory(id).subscribe(products => this.products = products);
  }

  getServices(id: number) {
    this.serviceService.getServicesByCategory(id).subscribe(services => this.services = services);
  }
}
