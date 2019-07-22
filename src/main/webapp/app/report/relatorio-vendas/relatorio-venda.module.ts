import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VestdeskSharedModule } from '../../shared';
import {
    RelatorioVendaService,
    RelatorioVendaPopupService,
    RelatorioVendaComponent,
    relatorioVendaRoute,
    relatorioVendaPopupRoute,
} from '.';

const ENTITY_STATES = [
    ...relatorioVendaRoute,
    ...relatorioVendaPopupRoute,
];

@NgModule({
    imports: [
        VestdeskSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RelatorioVendaComponent
    ],
    entryComponents: [
        RelatorioVendaComponent
    ],
    providers: [
        RelatorioVendaService,
        RelatorioVendaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VestdeskRelatorioVendaModule {}
