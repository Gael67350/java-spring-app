import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { User } from '../models/user';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private userSubject: BehaviorSubject<User>;
  private user: Observable<User>;

  constructor(private router: Router, private http: HttpClient) {
    this.userSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('user')));
    this.user = this.userSubject.asObservable();
  }

  public get currentUserValue(): User | null {
    return this.userSubject.value;
  }

  public get currentUserObservable(): BehaviorSubject<User> | null {
    return this.userSubject;
  }

  login(mail: string, password: string) {
    let formData = new FormData();
    formData.append('mail', mail);
    formData.append('password', password);

    return this.http.post<any>(`http://localhost:4200/auth/token`, formData)
      .pipe(map(data => {
        data.authdata = window.btoa(mail + ':' + password);
        data.user.token = data.token;
        localStorage.setItem('user', JSON.stringify(data.user));
        this.userSubject.next(data.user);
        return data.user;
      }));
  }

  logout() {
    console.log("logout");
    return this.http.post<any>(`http://localhost:4200/auth/logout`, null)
      .subscribe(() => {
        localStorage.removeItem('user');
        this.userSubject.next(null);
        this.router.navigate(['/login']);
      });
  }
}
