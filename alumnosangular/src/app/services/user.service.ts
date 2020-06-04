import { LoginForm } from './../model/loginForm';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ResponseLogin } from '../model/responseLogin';
import { Subject, Observable } from 'rxjs';

@Injectable()
export class UserService{
  private token: string;
  private token$ = new Subject<string>();

  constructor(
    private httpClient: HttpClient
  ){
    this.token = '';
  }

  public getToken():string{
    return this.token;
  }

  public getData(): Observable<string>{
    return this.token$.asObservable();
  }

  public postLogin(user: LoginForm){
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    this.httpClient.post("http://192.168.5.234:8080/api/authenticate",JSON.stringify(user), httpOptions).subscribe(
      (response: ResponseLogin) => {
        //console.log(response);
        this.token = response.id_token;
        this.token$.next(this.token);
      },
      error => {
        console.log(error);
      }
    );
  }

}
