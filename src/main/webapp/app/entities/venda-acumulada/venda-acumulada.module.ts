import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VestdeskSharedModule } from '../../shared';
import {
    VendaAcumuladaService,
    VendaAcumuladaPopupService,
    VendaAcumuladaComponent,
    VendaAcumuladaDetailComponent,
    VendaAcumuladaDialogComponent,
    VendaAcumuladaPopupComponent,
    VendaAcumuladaDeletePopupComponent,
    VendaAcumuladaDeleteDialogComponent,
    vendaAcumuladaRoute,
    vendaAcumuladaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...vendaAcumuladaRoute,
    ...vendaAcumuladaPopupRoute,
];

@NgModule({
    imports: [
        VestdeskSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        VendaAcumuladaComponent,
        VendaAcumuladaDetailComponent,
        VendaAcumuladaDialogComponent,
        VendaAcumuladaDeleteDialogComponent,
        VendaAcumuladaPopupComponent,
        VendaAcumuladaDeletePopupComponent,
    ],
    entryComponents: [
        VendaAcumuladaComponent,
        VendaAcumuladaDialogComponent,
        VendaAcumuladaPopupComponent,
        VendaAcumuladaDeleteDialogComponent,
        VendaAcumuladaDeletePopupComponent,
    ],
    providers: [
        VendaAcumuladaService,
        VendaAcumuladaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VestdeskVendaAcumuladaModule {}
