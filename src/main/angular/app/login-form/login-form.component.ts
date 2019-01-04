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
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit{

  submitted = false;

  statusCode = 0;
  message = '';


  model = new User('', '');


  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private router: Router,
    private token: TokenStorage,
    private authenticationService: AuthenticationService) {}


  onSubmit() {
    this.submitted = true;

    this.authenticationService.login(this.model.name,this.model.password).subscribe(data=>{
      if (data.token!=null) {
          this.token.setUsername(this.model.name);
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





  }

  ngOnInit() {

  }

  logout(){
    this.authenticationService.logout();
    this.router.navigateByUrl('/login');
  }

}
