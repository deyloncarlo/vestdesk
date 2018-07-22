import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VestdeskSharedModule } from '../../shared';
import {
    ModeloService,
    ModeloPopupService,
    ModeloComponent,
    ModeloDetailComponent,
    ModeloDialogComponent,
    ModeloPopupComponent,
    ModeloDeletePopupComponent,
    ModeloDeleteDialogComponent,
    modeloRoute,
    modeloPopupRoute,
} from './';

const ENTITY_STATES = [
    ...modeloRoute,
    ...modeloPopupRoute,
];

@NgModule({
    imports: [
        VestdeskSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ModeloComponent,
        ModeloDetailComponent,
        ModeloDialogComponent,
        ModeloDeleteDialogComponent,
        ModeloPopupComponent,
        ModeloDeletePopupComponent,
    ],
    entryComponents: [
        ModeloComponent,
        ModeloDialogComponent,
        ModeloPopupComponent,
        ModeloDeleteDialogComponent,
        ModeloDeletePopupComponent,
    ],
    providers: [
        ModeloService,
        ModeloPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VestdeskModeloModule {}
