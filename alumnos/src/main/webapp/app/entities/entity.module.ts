import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'alumno',
        loadChildren: () => import('./alumno/alumno.module').then(m => m.AlumnosAlumnoModule)
      },
      {
        path: 'expediente',
        loadChildren: () => import('./expediente/expediente.module').then(m => m.AlumnosExpedienteModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class AlumnosEntityModule {}
