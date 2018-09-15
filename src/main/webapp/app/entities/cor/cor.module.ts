import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VestdeskSharedModule } from '../../shared';
import {
    CorService,
    CorPopupService,
    CorComponent,
    CorDetailComponent,
    CorDialogComponent,
    CorPopupComponent,
    CorDeletePopupComponent,
    CorDeleteDialogComponent,
    corRoute,
    corPopupRoute,
} from './';

const ENTITY_STATES = [
    ...corRoute,
    ...corPopupRoute,
];

@NgModule({
    imports: [
        VestdeskSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CorComponent,
        CorDetailComponent,
        CorDialogComponent,
        CorDeleteDialogComponent,
        CorPopupComponent,
        CorDeletePopupComponent,
    ],
    entryComponents: [
        CorComponent,
        CorDialogComponent,
        CorPopupComponent,
        CorDeleteDialogComponent,
        CorDeletePopupComponent,
    ],
    providers: [
        CorService,
        CorPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VestdeskCorModule {}
