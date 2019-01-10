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
  servicesCount: number;

  constructor(private serviceService: ServiceService) { }

  ngOnInit() {
    this.getServices();
  }

  changePage(page: number) {
    this.serviceService.getServices(page).subscribe(data => {
      this.services = data['listOfServices']
    });
    return page;
  }

  getServices() {
    this.serviceService.getServices(1).subscribe(data => {
      this.services = data['listOfServices'];
      this.servicesCount = data['sumOfServices'];
    });
  }

  onFilterChange(event) {
    this.serviceService.getServicesSortedByName(event.target.value)
        .subscribe(data => {
      this.servicesCount = data['sumOfServices'];
      this.services = data['listOfServices'];
    });
  }
}
