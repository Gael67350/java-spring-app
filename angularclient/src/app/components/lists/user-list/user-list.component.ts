import { Component, OnInit } from '@angular/core';
import { User } from '../../../models/user';
import { UserService } from '../../../services/user.service';

import { AuthenticationService } from '../../../services/authentication.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {

  private static readonly alertDanger = "alert-danger";
  private static readonly alertSuccess = "alert-success";

  me: User;
  users: User[];
  searchQuery: string;
  alertMessage: string;
  alertClass: string;

  constructor(private userService: UserService, 
    authenticationService: AuthenticationService
    ) {
    this.me = authenticationService.currentUserValue;
    this.users = [];
    this.searchQuery = "";

    this.alertMessage = "";
    this.alertClass = UserListComponent.alertDanger;
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

  onDeleteItem(user: User) {
    if(confirm("Voulez-vous vraiment supprimer le compte " + user.mail)) {
      this.userService.delete(user.id).subscribe(() => {
        this.alertMessage = `L'utilisateur ${user.mail} a bien été supprimé !`;
        this.alertClass = UserListComponent.alertSuccess;
        setTimeout(() => this.alertMessage = "", 5000);

        this.users = this.users.filter(u => u.id !== user.id);
      },
      (error) => {
        this.alertMessage = error;
        this.alertClass = UserListComponent.alertDanger;
        setTimeout(() => this.alertMessage = "", 5000);
      });
    }
  }

}
