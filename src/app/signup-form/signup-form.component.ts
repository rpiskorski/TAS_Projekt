import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { User } from '../user';
import { UserService } from '../user.service';
import { MessageService } from '../message.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup-form',
  templateUrl: './signup-form.component.html',
  styleUrls: ['./signup-form.component.css']
})
export class SignupFormComponent implements OnInit {

  submitted = false;

  model = new User(0, '', '');

  secondPassword = '';

  onSubmit() {
    this.submitted = true;
    this.userService.register(this.model).subscribe();
  }

  constructor(private authService: AuthService,
              private userService: UserService,
              private messageService: MessageService,
              private router: Router) { }

  ngOnInit() {
  }

  passwordCheck(): boolean{
    return this.model.password==this.secondPassword;
  }

  // redirect(url: string): void{
  //   setTimeout(() => {
  //     this.router.navigate([url]);
  //   },5000);
  // }

}
