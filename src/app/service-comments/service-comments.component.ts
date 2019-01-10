import { Component, OnInit } from '@angular/core';
import { Comment } from '../comment';
import { User } from '../user';
import { ServiceService } from '../service.service';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-service-comments',
  templateUrl: './service-comments.component.html',
  styleUrls: ['./service-comments.component.css']
})
export class ServiceCommentsComponent implements OnInit {

  serviceComments: Comment[];

  submitted = false;

  isLoggedIn = false;

  model = new Comment(0, 0, '', 0, new User(0, '', ''));

  onSubmit() {
    this.submitted = true;
    const id = +this.route.snapshot.paramMap.get('id');
    this.serviceService.addComment(this.model, id).subscribe();
  }

  constructor(
    private serviceService: ServiceService,
    private route: ActivatedRoute,
    private authService: AuthService) { }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.getComments(id);
    this.isLoggedIn = this.authService.isAuthenticated();
  }

  getComments(id: number) {
    this.serviceService.getServiceComments(id).subscribe(data => {

      for (const comment of data['listOfComments']) {
        comment['timestamp'] = new Date(comment['timestamp']).toLocaleString();
      }

      this.serviceComments = data['listOfComments'];
    });
  }

  delete(comment: Comment) {
    const id = +this.route.snapshot.paramMap.get('id');
    this.serviceService.deleteComment(comment, id).subscribe();
    this.getComments(id);
  }

}
