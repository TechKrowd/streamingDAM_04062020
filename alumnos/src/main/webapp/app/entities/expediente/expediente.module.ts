import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AlumnosSharedModule } from 'app/shared/shared.module';
import { ExpedienteComponent } from './expediente.component';
import { ExpedienteDetailComponent } from './expediente-detail.component';
import { ExpedienteUpdateComponent } from './expediente-update.component';
import { ExpedienteDeleteDialogComponent } from './expediente-delete-dialog.component';
import { expedienteRoute } from './expediente.route';

@NgModule({
  imports: [AlumnosSharedModule, RouterModule.forChild(expedienteRoute)],
  declarations: [ExpedienteComponent, ExpedienteDetailComponent, ExpedienteUpdateComponent, ExpedienteDeleteDialogComponent],
  entryComponents: [ExpedienteDeleteDialogComponent]
})
export class AlumnosExpedienteModule {}
