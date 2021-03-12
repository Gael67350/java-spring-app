import { Component, OnInit } from '@angular/core';
import { User } from './models/user';

import { AuthenticationService } from './services/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'Spring Boot - Angular Client';
  loggedUser?: User;

  constructor(private authenticationService: AuthenticationService) {}

  ngOnInit(): void {
    let that = this;

    this.authenticationService.currentUserObservable.subscribe({
      next(userData) {
        that.loggedUser = userData;
      }
    });
  }

  onClickLogout() {
    this.authenticationService.logout();
  }
}
