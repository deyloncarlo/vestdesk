import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VestdeskSharedModule } from '../../shared';
import {
    AdiantamentoService,
    AdiantamentoPopupService,
    AdiantamentoComponent,
    AdiantamentoDetailComponent,
    AdiantamentoDialogComponent,
    AdiantamentoPopupComponent,
    AdiantamentoDeletePopupComponent,
    AdiantamentoDeleteDialogComponent,
    adiantamentoRoute,
    adiantamentoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...adiantamentoRoute,
    ...adiantamentoPopupRoute,
];

@NgModule({
    imports: [
        VestdeskSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AdiantamentoComponent,
        AdiantamentoDetailComponent,
        AdiantamentoDialogComponent,
        AdiantamentoDeleteDialogComponent,
        AdiantamentoPopupComponent,
        AdiantamentoDeletePopupComponent,
    ],
    entryComponents: [
        AdiantamentoComponent,
        AdiantamentoDialogComponent,
        AdiantamentoPopupComponent,
        AdiantamentoDeleteDialogComponent,
        AdiantamentoDeletePopupComponent,
    ],
    providers: [
        AdiantamentoService,
        AdiantamentoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VestdeskAdiantamentoModule {}
