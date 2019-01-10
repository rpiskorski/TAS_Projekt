import { Component, OnInit } from '@angular/core';

import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-signup-form',
  templateUrl: './signup-form.component.html',
  styleUrls: ['./signup-form.component.css']
})
export class SignupFormComponent implements OnInit {

  submitted = false;

  model = new User(0, '', '');

  onSubmit() {
    this.submitted = true;
    this.userService.register(this.model).subscribe();
  }

  constructor(private userService: UserService) { }

  ngOnInit() {
  }

}
