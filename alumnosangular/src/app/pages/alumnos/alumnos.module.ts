import { NgModule } from "@angular/core";
import { CommonModule } from '@angular/common';
import { AlumnosComponent } from './alumnos.component';

@NgModule({
  declarations: [AlumnosComponent],
  imports: [CommonModule],
  exports: [AlumnosComponent],
  providers: []
})

export class AlumnosModule{
  constructor(){}
}
