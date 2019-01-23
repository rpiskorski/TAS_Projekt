import { Component, OnInit, EventEmitter, Output } from '@angular/core';

import { User } from '../user';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  submitted = false;

  model = new User(0, '', '');

  constructor(private authService: AuthService, private router: Router, private messageService: MessageService) { }

  onSubmit() {
    this.submitted = true;

    this.authService.login(this.model).subscribe(data =>
      {
        if(data.token!=null){
            this.authService.setUsername(this.model.name);
            this.router.navigate(['profile']);
        }
        //return data;
      }
      //,error => {}
        // console.log(this.messageService.message);

      );


    //   if (!(data instanceof HttpErrorResponse)) {
     //    this.router.navigate(['profile']);
    //   }



    }


  ngOnInit() {
    // this.authService.logout();
  }

  redirect(url: string): void{
    setTimeout(() => {
      this.router.navigate([url]);
    },5000);
  }

  logout(){
  this.authService.logout();
  this.router.navigate(['login-form']);
}

}
