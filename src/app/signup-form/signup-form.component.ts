import { Component, OnInit } from '@angular/core';

import { User } from '../user';

@Component({
  selector: 'app-signup-form',
  templateUrl: './signup-form.component.html',
  styleUrls: ['./signup-form.component.css']
})
export class SignupFormComponent implements OnInit {

  submitted = false;

  model = new User('', '');

  onSubmit() {
    this.submitted = true;
  }

  constructor() { }

  ngOnInit() {
  }

}
