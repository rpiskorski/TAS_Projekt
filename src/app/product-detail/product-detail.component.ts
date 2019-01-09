import { Component, OnInit } from '@angular/core';
import { Product } from '../product';
import { ProductService } from '../product.service';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../auth.service';
import { Comment } from '../comment';
import { User } from '../user';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {

  product: Product;

  submitted = false;

  loggedIn = false;

  // model = new Comment(null, null, null, null, null);
  model = new Comment(0, 0, '', 0, new User('', ''));

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private authService: AuthService) { }

  ngOnInit() {
    this.getProduct();
    this.loggedIn = this.authService.isAuthenticated();
  }

  getProduct() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.productService.getProduct(id).subscribe(data => {
      this.product = data['product'];
    });
  }

  delete() {
    this.productService.delete(this.product).subscribe();
  }

  onSubmit() {
    this.submitted = true;
    const id = +this.route.snapshot.paramMap.get('id');
    this.productService.addComment(this.model, id).subscribe();
  }
}
