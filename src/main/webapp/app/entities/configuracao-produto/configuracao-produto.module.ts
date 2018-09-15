import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VestdeskSharedModule } from '../../shared';
import {
    ConfiguracaoProdutoService,
    ConfiguracaoProdutoPopupService,
    ConfiguracaoProdutoComponent,
    ConfiguracaoProdutoDetailComponent,
    ConfiguracaoProdutoDialogComponent,
    ConfiguracaoProdutoPopupComponent,
    ConfiguracaoProdutoDeletePopupComponent,
    ConfiguracaoProdutoDeleteDialogComponent,
    configuracaoProdutoRoute,
    configuracaoProdutoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...configuracaoProdutoRoute,
    ...configuracaoProdutoPopupRoute,
];

@NgModule({
    imports: [
        VestdeskSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ConfiguracaoProdutoComponent,
        ConfiguracaoProdutoDetailComponent,
        ConfiguracaoProdutoDialogComponent,
        ConfiguracaoProdutoDeleteDialogComponent,
        ConfiguracaoProdutoPopupComponent,
        ConfiguracaoProdutoDeletePopupComponent,
    ],
    entryComponents: [
        ConfiguracaoProdutoComponent,
        ConfiguracaoProdutoDialogComponent,
        ConfiguracaoProdutoPopupComponent,
        ConfiguracaoProdutoDeleteDialogComponent,
        ConfiguracaoProdutoDeletePopupComponent,
    ],
    providers: [
        ConfiguracaoProdutoService,
        ConfiguracaoProdutoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VestdeskConfiguracaoProdutoModule {}
