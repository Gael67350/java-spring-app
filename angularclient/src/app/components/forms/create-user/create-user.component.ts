import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../../services/user.service';
import { User } from '../../../models/user';
import { ConfirmedValidator } from '../../../validators/confirmed.validator';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.scss']
})
export class CreateUserComponent implements OnInit {
  user: User;
  userForm: FormGroup;
  loading = false;
  submitted = false;
  error = "";

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.userForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      mail: ['', Validators.compose([Validators.required, Validators.email])],
      password: ['', Validators.required],
      passwordConfirmation: ['', Validators.required]
    }, { validators: ConfirmedValidator('password', 'passwordConfirmation') });
  }

  get f() { return this.userForm.controls; }

  onSubmit() {
    this.submitted = true;

    if(this.userForm.invalid) {
      return;
    }

    this.loading = true;

    let user = new User(
      null,
      this.f.firstName.value,
      this.f.lastName.value,
      this.f.mail.value,
      this.f.password.value
      );

    this.userService.create(user)
    .subscribe(data => {
      this.router.navigate(['/users']);
    },
    error => {
      this.error = error;
      this.loading = false;
    });
  }
}
