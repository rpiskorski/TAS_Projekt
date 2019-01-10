import { Component, OnInit } from '@angular/core';
import { ProductService } from '../product.service';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../auth.service';
import { Comment } from '../comment';
import { User } from '../user';

@Component({
  selector: 'app-product-comments',
  templateUrl: './product-comments.component.html',
  styleUrls: ['./product-comments.component.css']
})
export class ProductCommentsComponent implements OnInit {

  productComments: Comment[];

  submitted = false;

  isLoggedIn = false;

  model = new Comment(0, 0, '', 0, new User(0, '', ''));

  onSubmit() {
    this.submitted = true;
    const id = +this.route.snapshot.paramMap.get('id');
    this.productService.addComment(this.model, id).subscribe();
  }

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

      if (productComments.length === 0) {
        this.productComments = [];
        return;
      }

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
