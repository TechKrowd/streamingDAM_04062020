import { AlumnoService } from './../../services/alumno.service';
import { UserService } from './../../services/user.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-alumnos',
  templateUrl: './alumnos.component.html',
  styleUrls: ['./alumnos.component.css']
})
export class AlumnosComponent implements OnInit {
  public alumnos: Array<any>;
  constructor(
    private router: Router,
    private alumnoService: AlumnoService
  ) {
    this.alumnos = new Array<any>();
    this.alumnoService.getData().subscribe(
      response => {
        this.alumnos = response;
      },
      error => {
        console.log(error);
        this.router.navigate(['/home']);
      }
    );
  }

  ngOnInit(): void {
    this.alumnoService.getAlumnos();
  }

}
