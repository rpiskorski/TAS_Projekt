import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent implements OnInit {

  private user: User;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService) { }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.userService.getUser(id).subscribe(data => {
      this.user = data['user'];
    });
  }

  onSubmit() {
    this.router.navigate(['users']);
  }
}
