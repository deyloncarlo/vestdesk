import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VestdeskSharedModule } from '../shared';

import { HOME_ROUTE, HomeComponent } from './';

import {
    pedidoRoute,
    pedidoPopupRoute,
} from './../entities/pedido';

@NgModule({
    imports: [
        VestdeskSharedModule,
        RouterModule.forChild([ HOME_ROUTE, ...pedidoRoute, ...pedidoPopupRoute ])
    ],
    declarations: [
        HomeComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VestdeskHomeModule {}
