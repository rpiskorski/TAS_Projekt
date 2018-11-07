import { Component, OnInit } from '@angular/core';
import { Service } from '../service';
import { ServiceService } from '../service.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-service-detail',
  templateUrl: './service-detail.component.html',
  styleUrls: ['./service-detail.component.css']
})
export class ServiceDetailComponent implements OnInit {

  service: Service;

  constructor(
    private route: ActivatedRoute,
    private serviceService: ServiceService
  ) { }

  ngOnInit() {
    this.getService();
  }

  getService() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.serviceService.getService(id).subscribe(service => this.service = { ...service });
  }
}
