import { Component, OnInit } from '@angular/core';
import { ProductService } from '../product.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-product-comments',
  templateUrl: './product-comments.component.html',
  styleUrls: ['./product-comments.component.css']
})
export class ProductCommentsComponent implements OnInit {

  productComments: Comment[];

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute) { }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.getComments(id);
  }

  getComments(id: number) {
    this.productService.getProductComments(id).subscribe(productComments => {
      this.productComments = productComments['listOfComments'];
    });
  }
}
