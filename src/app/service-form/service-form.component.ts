import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../service.service';
import { CategoryService } from '../category.service';
import { Category } from '../category';
import { Service } from '../service';

@Component({
  selector: 'app-service-form',
  templateUrl: './service-form.component.html',
  styleUrls: ['./service-form.component.css']
})
export class ServiceFormComponent implements OnInit {

  categories: Category[];

  submitted = false;

  model = new Service(0, '', '', '', 0, 0, new Category(0, ''));

  constructor(
    private serviceService: ServiceService,
    private categoryService: CategoryService) { }

  ngOnInit() {
    this.getCategories();
  }

  onSubmit() {
    this.submitted = true;
    this.serviceService.add(this.model).subscribe();
  }

  getCategories() {
    this.categoryService.getCategories().subscribe(categories => this.categories = categories);
  }
}
