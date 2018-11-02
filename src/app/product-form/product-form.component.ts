import { Component, OnInit } from '@angular/core';
import { ProductService } from '../product.service';
import { Product } from '../product';

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.css']
})
export class ProductFormComponent implements OnInit {

  submitted = false;

  model = new Product();

  constructor(private productService: ProductService) { }

  ngOnInit() {
  }

  onSubmit() {
    this.submitted = true;
    // console.log(this.model);
  }


}
