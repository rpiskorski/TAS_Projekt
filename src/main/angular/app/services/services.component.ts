import { Component, OnInit } from '@angular/core';

import { ServiceService } from '../service.service';
import { Service } from '../service';


@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrls: ['./services.component.css']
})
export class ServicesComponent implements OnInit {

  services: Service[];

  constructor(private serviceService: ServiceService) { }

  ngOnInit() {
    this.getServices();
  }

  changePage(page: number) {
    this.serviceService.getServices(page).subscribe(services => this.services = services);
    return page;
  }

  getServices() {
    this.serviceService.getServices(1).subscribe(services => this.services = services);
  }
}
