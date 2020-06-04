import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { AlumnosSharedModule } from 'app/shared/shared.module';
import { AlumnosCoreModule } from 'app/core/core.module';
import { AlumnosAppRoutingModule } from './app-routing.module';
import { AlumnosHomeModule } from './home/home.module';
import { AlumnosEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    AlumnosSharedModule,
    AlumnosCoreModule,
    AlumnosHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    AlumnosEntityModule,
    AlumnosAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class AlumnosAppModule {}
