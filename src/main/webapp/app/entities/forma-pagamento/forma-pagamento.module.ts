import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VestdeskSharedModule } from '../../shared';
import {
    FormaPagamentoService,
    FormaPagamentoPopupService,
    FormaPagamentoComponent,
    FormaPagamentoDetailComponent,
    FormaPagamentoDialogComponent,
    FormaPagamentoPopupComponent,
    FormaPagamentoDeletePopupComponent,
    FormaPagamentoDeleteDialogComponent,
    formaPagamentoRoute,
    formaPagamentoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...formaPagamentoRoute,
    ...formaPagamentoPopupRoute,
];

@NgModule({
    imports: [
        VestdeskSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FormaPagamentoComponent,
        FormaPagamentoDetailComponent,
        FormaPagamentoDialogComponent,
        FormaPagamentoDeleteDialogComponent,
        FormaPagamentoPopupComponent,
        FormaPagamentoDeletePopupComponent,
    ],
    entryComponents: [
        FormaPagamentoComponent,
        FormaPagamentoDialogComponent,
        FormaPagamentoPopupComponent,
        FormaPagamentoDeleteDialogComponent,
        FormaPagamentoDeletePopupComponent,
    ],
    providers: [
        FormaPagamentoService,
        FormaPagamentoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VestdeskFormaPagamentoModule {}
