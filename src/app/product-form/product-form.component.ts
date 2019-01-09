import { Component, OnInit } from '@angular/core';
import { ProductService } from '../product.service';
import { Product } from '../product';
import { CategoryService } from '../category.service';
import { Category } from '../category';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.css']
})
export class ProductFormComponent implements OnInit {

  categories: Category[];

  submitted = false;

  model = new Product(0, '', '', 0, 0, new Category(0, ''));

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService) { }

  ngOnInit() {
    this.getCategories();
  }

  onSubmit() {
    this.submitted = true;
    this.productService.add(this.model).subscribe();
  }

  getCategories() {
    this.categoryService.getCategories().subscribe(data => {
      this.categories = data['listOfCategories'];
    });
  }
}
