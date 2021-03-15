import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from '../../../services/user.service';
import { User } from '../../../models/user';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss']
})
export class EditUserComponent implements OnInit {
  userId: number;
  user: User;
  userForm: FormGroup;
  loading = false;
  submitted = false;
  error = "";

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(map => {
      this.userId = parseInt(map.get('id'));
    });

    this.userForm = this.formBuilder.group({
      id: [this.userId],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      mail: ['', Validators.compose([Validators.required, Validators.email])],
      password: [''],
      passwordConfirmation: ['']
    }, { validators: this.passwordsMatch });

    this.userService.find(this.userId)
    .subscribe(data => {
      this.userForm.patchValue({
        id: data.id,
        firstName: data.firstName,
        lastName: data.lastName,
        mail: data.mail
      });
    },
    error => {
      this.router.navigate(['/users']);
    });
  }

  get f() { return this.userForm.controls; }

  onSubmit() {
    this.submitted = true;

    if(this.userForm.invalid) {
      return;
    }

    this.loading = true;

    let user = new User(
      this.f.id.value, 
      this.f.firstName.value,
      this.f.lastName.value,
      this.f.mail.value,
      this.f.password.value !== "" ? this.f.password.value : null
      );

    this.userService.update(user, this.f.id.value)
    .subscribe(data => {
      this.router.navigate(['/users']);
    },
    error => {
      this.error = error;
      this.loading = false;
    });
  }

  passwordsMatch(group: FormGroup) {
    const pwd = group.get('password').value;
    const pwdConf = group.get('passwordConfirmation').value;

    if(pwd !== pwdConf) {
      group.controls['passwordConfirmation'].setErrors({ 'notSame': true });
    }else{
      group.controls['passwordConfirmation'].setErrors(null);
    }
  }

}
