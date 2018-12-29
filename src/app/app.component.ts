import { Component, OnInit } from '@angular/core';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  private authenticated = false;

  constructor(private authService: AuthService) {}

  ngOnInit() {
    this.authenticated = this.authService.isAuthenticated();
  }

  logout() {
    this.authenticated = false;
    this.authService.logout();
  }

  onLoggedIn(czo: boolean) {
    console.log("hi");
    this.authenticated = czo;
  }
}
