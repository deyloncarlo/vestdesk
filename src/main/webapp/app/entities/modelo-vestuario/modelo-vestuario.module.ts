import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VestdeskSharedModule } from '../../shared';
import {
    ModeloVestuarioService,
    ModeloVestuarioPopupService,
    ModeloVestuarioComponent,
    ModeloVestuarioDetailComponent,
    ModeloVestuarioDialogComponent,
    ModeloVestuarioPopupComponent,
    ModeloVestuarioDeletePopupComponent,
    ModeloVestuarioDeleteDialogComponent,
    modeloVestuarioRoute,
    modeloVestuarioPopupRoute,
} from './';

const ENTITY_STATES = [
    ...modeloVestuarioRoute,
    ...modeloVestuarioPopupRoute,
];

@NgModule({
    imports: [
        VestdeskSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ModeloVestuarioComponent,
        ModeloVestuarioDetailComponent,
        ModeloVestuarioDialogComponent,
        ModeloVestuarioDeleteDialogComponent,
        ModeloVestuarioPopupComponent,
        ModeloVestuarioDeletePopupComponent,
    ],
    entryComponents: [
        ModeloVestuarioComponent,
        ModeloVestuarioDialogComponent,
        ModeloVestuarioPopupComponent,
        ModeloVestuarioDeleteDialogComponent,
        ModeloVestuarioDeletePopupComponent,
    ],
    providers: [
        ModeloVestuarioService,
        ModeloVestuarioPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VestdeskModeloVestuarioModule {}
