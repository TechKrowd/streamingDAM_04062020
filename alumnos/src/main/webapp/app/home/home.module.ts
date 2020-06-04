import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AlumnosSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [AlumnosSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class AlumnosHomeModule {}
