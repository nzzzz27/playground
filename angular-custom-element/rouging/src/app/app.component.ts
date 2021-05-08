import { Component, Injector } from '@angular/core';
import { createCustomElement } from '@angular/elements';
import {DashboardComponent} from './dashboard/dashboard.component';

@Component({
  selector: 'app-bootstrap',
  templateUrl: './app.component.html',
})
export class AppComponent {
  title = 'custom-element-routing';

  constructor(
    private injector: Injector
  ) {
    const appComponent = createCustomElement(
      DashboardComponent, { injector: this.injector }
    );
    customElements.define('app-dashboard', appComponent);
  }

}
