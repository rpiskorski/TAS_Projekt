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
    this.serviceService.getServicesByCategory(id).subscribe(services => this.services = services);
  }

  changePage(page: number) {
    const id = +this.route.snapshot.paramMap.get('id');
    this.productService.getProductsByCategory(id, page).subscribe(products => {
      this.productCount = products['sumOfProducts'];
      this.products = products['listOfProducts'];
    });

    return page;
  }
}
