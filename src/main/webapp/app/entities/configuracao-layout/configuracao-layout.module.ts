import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VestdeskSharedModule } from '../../shared';
import {
    ConfiguracaoLayoutService,
    ConfiguracaoLayoutPopupService,
    ConfiguracaoLayoutComponent,
    ConfiguracaoLayoutDetailComponent,
    ConfiguracaoLayoutDialogComponent,
    ConfiguracaoLayoutPopupComponent,
    ConfiguracaoLayoutDeletePopupComponent,
    ConfiguracaoLayoutDeleteDialogComponent,
    configuracaoLayoutRoute,
    configuracaoLayoutPopupRoute,
} from './';

const ENTITY_STATES = [
    ...configuracaoLayoutRoute,
    ...configuracaoLayoutPopupRoute,
];

@NgModule({
    imports: [
        VestdeskSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ConfiguracaoLayoutComponent,
        ConfiguracaoLayoutDetailComponent,
        ConfiguracaoLayoutDialogComponent,
        ConfiguracaoLayoutDeleteDialogComponent,
        ConfiguracaoLayoutPopupComponent,
        ConfiguracaoLayoutDeletePopupComponent,
    ],
    entryComponents: [
        ConfiguracaoLayoutComponent,
        ConfiguracaoLayoutDialogComponent,
        ConfiguracaoLayoutPopupComponent,
        ConfiguracaoLayoutDeleteDialogComponent,
        ConfiguracaoLayoutDeletePopupComponent,
    ],
    providers: [
        ConfiguracaoLayoutService,
        ConfiguracaoLayoutPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VestdeskConfiguracaoLayoutModule {}
