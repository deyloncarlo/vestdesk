import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VestdeskSharedModule } from '../../shared';
import {
    QuantidadeTamanhoService,
    QuantidadeTamanhoPopupService,
    QuantidadeTamanhoComponent,
    QuantidadeTamanhoDetailComponent,
    QuantidadeTamanhoDialogComponent,
    QuantidadeTamanhoPopupComponent,
    QuantidadeTamanhoDeletePopupComponent,
    QuantidadeTamanhoDeleteDialogComponent,
    quantidadeTamanhoRoute,
    quantidadeTamanhoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...quantidadeTamanhoRoute,
    ...quantidadeTamanhoPopupRoute,
];

@NgModule({
    imports: [
        VestdeskSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        QuantidadeTamanhoComponent,
        QuantidadeTamanhoDetailComponent,
        QuantidadeTamanhoDialogComponent,
        QuantidadeTamanhoDeleteDialogComponent,
        QuantidadeTamanhoPopupComponent,
        QuantidadeTamanhoDeletePopupComponent,
    ],
    entryComponents: [
        QuantidadeTamanhoComponent,
        QuantidadeTamanhoDialogComponent,
        QuantidadeTamanhoPopupComponent,
        QuantidadeTamanhoDeleteDialogComponent,
        QuantidadeTamanhoDeletePopupComponent,
    ],
    providers: [
        QuantidadeTamanhoService,
        QuantidadeTamanhoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VestdeskQuantidadeTamanhoModule {}
