import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
// import { AppServiceService } from './app-service.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'tas';

  // constructor(
  //   private app: AppServiceService,
  //   private http: HttpClient,
  //   private router: Router ) {
  //     this.app.authenticate(undefined, undefined);
  //   }

  // logout() {
  //   // finally() ?
  //   this.http.post('logout', {}).lift(() => {
  //     this.app.authenticated = false;
  //     this.router.navigateByUrl('/login');
  //   }).subscribe();
  // }
}
