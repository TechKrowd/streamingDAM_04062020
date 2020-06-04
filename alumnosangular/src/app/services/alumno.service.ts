import { UserService } from './user.service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Subject, Observable } from 'rxjs';

@Injectable()
export class AlumnoService {
  private alumnos: Array<any>;
  private alumnos$;

  constructor(
    private httpClient: HttpClient,
    private userService: UserService
  ){
    this.alumnos = new Array<any>();
    this.alumnos$ = new Subject<Array<any>>();
  }

  public getListAlumnos():Array<any>{
    return this.alumnos;
  }

  public getData(): Observable<any>{
    return this.alumnos$.asObservable();
  }

  public getAlumnos(){

    const httpOptions  = {
      headers: new HttpHeaders({
        'Authorization': 'Bearer '+this.userService.getToken()
      })
    };
    this.httpClient.get("http://192.168.5.234:8080/api/alumnos",httpOptions).subscribe(
      (response: Array<any>) => {
        this.alumnos = response;
        this.alumnos$.next(this.alumnos);
      },
      error => {
        console.log(error.status);
      }
    );
  }
}
