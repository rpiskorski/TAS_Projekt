import { Component, OnInit } from '@angular/core';
import { Product } from "../product";
import { Input } from "@angular/core";

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  @Input() product: Product;
}
