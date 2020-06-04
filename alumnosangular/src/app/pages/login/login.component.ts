import { UserService } from './../../services/user.service';
import { LoginForm } from './../../model/loginForm';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public loginModel: LoginForm;
  constructor(
    private userService: UserService,
    private router: Router
  ) {
    this.loginModel = new LoginForm();
   }

  ngOnInit(): void {
    this.userService.getData().subscribe(
      response => {
        this.router.navigate(['/home/alumnos']);
      },
      error => {
        console.log(error);
      }
    );
  }

  onSubmit(f: NgForm){
    console.log(JSON.stringify(this.loginModel));
    this.userService.postLogin(this.loginModel);
  }

}
