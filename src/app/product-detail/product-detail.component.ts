import { Component, OnInit } from '@angular/core';
import { Product } from "../product";
import { Input } from "@angular/core";
import { ProductService } from '../product.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {

  product: Product;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService) { }

  ngOnInit() {
    this.getProduct();
  }

  getProduct(): void {
    const id = +this.route.snapshot.paramMap.get("id");
    this.productService.getProduct(id).subscribe(product => this.product = product);
  }

  // @Input() product: Product;
}
