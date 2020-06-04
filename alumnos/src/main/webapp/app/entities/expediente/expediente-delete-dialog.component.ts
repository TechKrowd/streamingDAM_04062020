import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExpediente } from 'app/shared/model/expediente.model';
import { ExpedienteService } from './expediente.service';

@Component({
  templateUrl: './expediente-delete-dialog.component.html'
})
export class ExpedienteDeleteDialogComponent {
  expediente?: IExpediente;

  constructor(
    protected expedienteService: ExpedienteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.expedienteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('expedienteListModification');
      this.activeModal.close();
    });
  }
}
