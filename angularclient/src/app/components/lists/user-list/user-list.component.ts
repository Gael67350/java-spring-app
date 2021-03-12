import { Component, OnInit } from '@angular/core';
import { User } from '../../../models/user';
import { UserService } from '../../../services/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {

  users: User[];
  searchQuery: string;

  constructor(private userService: UserService) {
    this.users = [];
    this.searchQuery = "";
  }

  ngOnInit(): void {
    this.userService.findAll().subscribe((data: User[]) => {
      this.users = data;
    });
  }

  onSearchStarted() {
    this.userService.search(this.searchQuery).subscribe((data: User[]) => {
      this.users = data;
    });

    this.searchQuery = "";
  }

}
