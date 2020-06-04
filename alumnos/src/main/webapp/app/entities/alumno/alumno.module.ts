import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AlumnosSharedModule } from 'app/shared/shared.module';
import { AlumnoComponent } from './alumno.component';
import { AlumnoDetailComponent } from './alumno-detail.component';
import { AlumnoUpdateComponent } from './alumno-update.component';
import { AlumnoDeleteDialogComponent } from './alumno-delete-dialog.component';
import { alumnoRoute } from './alumno.route';

@NgModule({
  imports: [AlumnosSharedModule, RouterModule.forChild(alumnoRoute)],
  declarations: [AlumnoComponent, AlumnoDetailComponent, AlumnoUpdateComponent, AlumnoDeleteDialogComponent],
  entryComponents: [AlumnoDeleteDialogComponent]
})
export class AlumnosAlumnoModule {}
