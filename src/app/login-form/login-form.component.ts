import { Component, OnInit } from '@angular/core';

import { User } from '../user';
// import { AppServiceService } from '../app-service.service';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  submitted = false;

  model = new User('', '');

  onSubmit() {
    this.submitted = true;
    // console.log(this.model.username);
    // console.log(this.model.password);

  }

  constructor(
    // private route: ActivatedRoute,
    // private http: HttpClient,
    // private router: Router
    ) { }

  ngOnInit() {
  }

  // login() {
  //   this.app.authenticate(this.model, () => {
  //     this.router.navigateByUrl('/profile');
  //   });
  //   return false;
  // }
}
