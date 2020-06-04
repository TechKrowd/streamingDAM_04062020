import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAlumno } from 'app/shared/model/alumno.model';
import { AlumnoService } from './alumno.service';

@Component({
  templateUrl: './alumno-delete-dialog.component.html'
})
export class AlumnoDeleteDialogComponent {
  alumno?: IAlumno;

  constructor(protected alumnoService: AlumnoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.alumnoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('alumnoListModification');
      this.activeModal.close();
    });
  }
}
