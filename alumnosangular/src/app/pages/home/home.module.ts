import { AlumnoService } from './../../services/alumno.service';
import { UserService } from './../../services/user.service';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home.component';

@NgModule({
  declarations: [HomeComponent],
  imports: [CommonModule, RouterModule],
  exports: [HomeComponent],
  providers: [
    UserService,
    AlumnoService
  ]
})

export class HomeModule{
  constructor(){}
}
