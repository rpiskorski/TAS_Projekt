import { Component, OnInit, EventEmitter, Output } from '@angular/core';

import { User } from '../user';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  submitted = false;

  model = new User('', '');

  @Output() loggedIn = new EventEmitter<boolean>();

  onSubmit() {
    this.submitted = true;
    this.authSerivce.login(this.model).subscribe(data => {
      if (!(data instanceof HttpErrorResponse)) {
        this.loggedIn.emit(true);
        this.router.navigate(['profile']);
      }
    });
  }

  constructor(
    private authSerivce: AuthService,
    private router: Router) { }

  ngOnInit() {}
}
