import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VestdeskSharedModule } from '../../shared';
import {
    MaterialService,
    MaterialPopupService,
    MaterialComponent,
    MaterialDetailComponent,
    MaterialDialogComponent,
    MaterialPopupComponent,
    MaterialDeletePopupComponent,
    MaterialDeleteDialogComponent,
    materialRoute,
    materialPopupRoute,
} from './';

const ENTITY_STATES = [
    ...materialRoute,
    ...materialPopupRoute,
];

@NgModule({
    imports: [
        VestdeskSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MaterialComponent,
        MaterialDetailComponent,
        MaterialDialogComponent,
        MaterialDeleteDialogComponent,
        MaterialPopupComponent,
        MaterialDeletePopupComponent,
    ],
    entryComponents: [
        MaterialComponent,
        MaterialDialogComponent,
        MaterialPopupComponent,
        MaterialDeleteDialogComponent,
        MaterialDeletePopupComponent,
    ],
    providers: [
        MaterialService,
        MaterialPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VestdeskMaterialModule {}
