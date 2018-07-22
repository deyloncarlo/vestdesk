import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VestdeskSharedModule } from '../../shared';
import {
    EtapaProducaoService,
    EtapaProducaoPopupService,
    EtapaProducaoComponent,
    EtapaProducaoDetailComponent,
    EtapaProducaoDialogComponent,
    EtapaProducaoPopupComponent,
    EtapaProducaoDeletePopupComponent,
    EtapaProducaoDeleteDialogComponent,
    etapaProducaoRoute,
    etapaProducaoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...etapaProducaoRoute,
    ...etapaProducaoPopupRoute,
];

@NgModule({
    imports: [
        VestdeskSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EtapaProducaoComponent,
        EtapaProducaoDetailComponent,
        EtapaProducaoDialogComponent,
        EtapaProducaoDeleteDialogComponent,
        EtapaProducaoPopupComponent,
        EtapaProducaoDeletePopupComponent,
    ],
    entryComponents: [
        EtapaProducaoComponent,
        EtapaProducaoDialogComponent,
        EtapaProducaoPopupComponent,
        EtapaProducaoDeleteDialogComponent,
        EtapaProducaoDeletePopupComponent,
    ],
    providers: [
        EtapaProducaoService,
        EtapaProducaoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VestdeskEtapaProducaoModule {}
