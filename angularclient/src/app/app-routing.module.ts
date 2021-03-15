import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserListComponent } from './components/lists/user-list/user-list.component';
import { CreateUserComponent } from './components/forms/create-user/create-user.component';
import { EditUserComponent } from './components/forms/edit-user/edit-user.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './helpers/auth.guard';

const routes: Routes = [
  { path: 'users', component: UserListComponent, canActivate: [AuthGuard] },
  { path: 'users/create', component: CreateUserComponent, canActivate: [AuthGuard] },
  { path: 'users/edit/:id', component: EditUserComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },

  // Fallback route
  { path: '**', redirectTo: 'users' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
