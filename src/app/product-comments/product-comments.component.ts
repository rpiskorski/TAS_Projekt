import { Component, OnInit } from '@angular/core';
import { ProductService } from '../product.service';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../auth.service';
import { Comment } from '../comment';

@Component({
  selector: 'app-product-comments',
  templateUrl: './product-comments.component.html',
  styleUrls: ['./product-comments.component.css']
})
export class ProductCommentsComponent implements OnInit {

  productComments: Comment[];

  isLoggedIn = false;

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute,
    private authService: AuthService) { }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.getComments(id);
    this.isLoggedIn = this.authService.isAuthenticated();
  }

  getComments(id: number) {
    this.productService.getProductComments(id).subscribe(productComments => {

      for (const comment of productComments['listOfComments']) {
        comment['timestamp'] = new Date(comment['timestamp']).toLocaleString();
      }

      this.productComments = productComments['listOfComments'];
    });
  }

  delete(comment: Comment) {
    const id = +this.route.snapshot.paramMap.get('id');
    this.productService.deleteComment(comment, id).subscribe();
    this.getComments(id);
  }
}
