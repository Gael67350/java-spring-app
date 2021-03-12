import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { NgxBootstrapIconsModule } from 'ngx-bootstrap-icons';
import { boxArrowInRight } from 'ngx-bootstrap-icons';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserListComponent } from './components/lists/user-list/user-list.component';
import { UserService } from './services/user.service';
import { LoginComponent } from './components/login/login.component';
import { BasicAuthInterceptor } from './helpers/basic-auth.interceptor';
import { ErrorInterceptor } from './helpers/error.interceptor';

const icons = {
  boxArrowInRight
};

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgxBootstrapIconsModule.pick(icons),
  ],
  providers: [
    UserService,

    { provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
