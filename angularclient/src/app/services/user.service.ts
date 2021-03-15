import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../models/user';
import { Observable } from 'rxjs/Observable'; 

@Injectable()
export class UserService {

  private usersUrl: string;

  constructor(private http: HttpClient) {
    this.usersUrl = 'http://localhost:4200/api/users';
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }

  public find(id: number) {
    return this.http.get<User>(this.usersUrl + `/${id}`);
  }

  public search(query: string) {
    return this.http.get<User[]>(this.usersUrl + `?q=${query}`);
  }

  public update(user: User, id: number) {
    let headers = {'Content-Type': 'application/json'};
    return this.http.put<User>(this.usersUrl + `/${id}`, JSON.stringify(user), {headers: headers});
  }

  public delete(id: number) {
    return this.http.delete<any>(this.usersUrl + `/${id}`);
  }
}
