import { NgModule } from "@angular/core";
import { CommonModule } from '@angular/common';
import { HomeModule } from './home/home.module';
import { LoginModule } from './login/login.module';
import { AlumnosModule } from './alumnos/alumnos.module';

@NgModule({
  declarations: [],
  imports: [CommonModule, HomeModule, LoginModule, AlumnosModule],
  exports: [HomeModule, LoginModule, AlumnosModule],
  providers: []
})

export class PagesModule{
  constructor(){}
}
