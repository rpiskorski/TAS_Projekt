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

  getServices(): void {
    this.serviceService.getServices().subscribe(services => this.services = services);
  }
}
