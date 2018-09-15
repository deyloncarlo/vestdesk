import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VestdeskSharedModule } from '../../shared';
import {
    MaterialTamanhoService,
    MaterialTamanhoPopupService,
    MaterialTamanhoComponent,
    MaterialTamanhoDetailComponent,
    MaterialTamanhoDialogComponent,
    MaterialTamanhoPopupComponent,
    MaterialTamanhoDeletePopupComponent,
    MaterialTamanhoDeleteDialogComponent,
    materialTamanhoRoute,
    materialTamanhoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...materialTamanhoRoute,
    ...materialTamanhoPopupRoute,
];

@NgModule({
    imports: [
        VestdeskSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MaterialTamanhoComponent,
        MaterialTamanhoDetailComponent,
        MaterialTamanhoDialogComponent,
        MaterialTamanhoDeleteDialogComponent,
        MaterialTamanhoPopupComponent,
        MaterialTamanhoDeletePopupComponent,
    ],
    entryComponents: [
        MaterialTamanhoComponent,
        MaterialTamanhoDialogComponent,
        MaterialTamanhoPopupComponent,
        MaterialTamanhoDeleteDialogComponent,
        MaterialTamanhoDeletePopupComponent,
    ],
    providers: [
        MaterialTamanhoService,
        MaterialTamanhoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VestdeskMaterialTamanhoModule {}
