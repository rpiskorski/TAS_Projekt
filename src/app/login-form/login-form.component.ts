import { Component, OnInit } from '@angular/core';

import { User } from '../user';

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
  }

  constructor() { }

  ngOnInit() {
  }

}
