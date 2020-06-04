import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExpediente } from 'app/shared/model/expediente.model';

@Component({
  selector: 'jhi-expediente-detail',
  templateUrl: './expediente-detail.component.html'
})
export class ExpedienteDetailComponent implements OnInit {
  expediente: IExpediente | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ expediente }) => (this.expediente = expediente));
  }

  previousState(): void {
    window.history.back();
  }
}
