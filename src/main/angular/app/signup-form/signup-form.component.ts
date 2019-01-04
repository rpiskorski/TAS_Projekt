import { Component, OnInit } from '@angular/core';

import { User } from '../user';
// import { AppServiceService } from '../app-service.service';
import { HttpClient, HttpErrorResponse} from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthenticationService } from '../auth.service';
import { TokenStorage } from '../token.storage';
import { catchError } from 'rxjs/operators';
import { tap } from 'rxjs/operators';

@Component({
  selector: 'app-signup-form',
  templateUrl: './signup-form.component.html',
  styleUrls: ['./signup-form.component.css']
})
export class SignupFormComponent implements OnInit {

  submitted = false;

  statusCode = 0;
  message = '';

  passCorrect = true;

  model = new User('', '');
  secondPassword = '';

  constructor(private token: TokenStorage,
              private authenticationService: AuthenticationService) {}


  onSubmit() {
    this.submitted = true;
    if(this.secondPassword==this.model.password){
    this.passCorrect=true;
    this.authenticationService.register(this.model.name,this.model.password).subscribe(data => {

    let code = this.token.getStatus();
    let message = this.token.getMessage();
    if(code && message){
        this.statusCode = code;
        this.message = message;
    }

     return data;
  },error => {


    let code = this.token.getStatus();
    let message = this.token.getMessage();
    if(code && message){
        this.statusCode = code;
        this.message = message;
    }

    }
  );
}else {this.passCorrect=false;}
}

  ngOnInit() {
  }

}
