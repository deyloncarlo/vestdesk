import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VestdeskSharedModule } from '../../shared';
import {
    LayoutService,
    LayoutPopupService,
    LayoutComponent,
    LayoutDetailComponent,
    LayoutDialogComponent,
    LayoutPopupComponent,
    LayoutDeletePopupComponent,
    LayoutDeleteDialogComponent,
    layoutRoute,
    layoutPopupRoute,
    LayoutInputComponent
} from './';

const ENTITY_STATES = [
    ...layoutRoute,
    ...layoutPopupRoute,
];

@NgModule({
    imports: [
        VestdeskSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LayoutComponent,
        LayoutDetailComponent,
        LayoutDialogComponent,
        LayoutDeleteDialogComponent,
        LayoutPopupComponent,
        LayoutDeletePopupComponent,
        LayoutInputComponent
    ],
    entryComponents: [
        LayoutComponent,
        LayoutDialogComponent,
        LayoutPopupComponent,
        LayoutDeleteDialogComponent,
        LayoutDeletePopupComponent,
        LayoutInputComponent
    ],
    providers: [
        LayoutService,
        LayoutPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VestdeskLayoutModule {}
