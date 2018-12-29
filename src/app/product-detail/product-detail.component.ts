import { Component, OnInit } from '@angular/core';
import { Product } from '../product';
import { Input } from '@angular/core';
import { ProductService } from '../product.service';
import { ActivatedRoute } from '@angular/router';
import { Category } from '../category';
import { ProductUser } from '../product-user';

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

  getProduct() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.productService.getProduct(id).subscribe(data => {
      this.product = data['product'];
    });
  }

  // getProductUsers() {
  //   const id = +this.route.snapshot.paramMap.get('id');
  //   this.productService.getProductUsers(id).subscribe(product )
  // }

  delete() {
    this.productService.delete(this.product).subscribe();
  }

  // @Input() product: Product;
}
