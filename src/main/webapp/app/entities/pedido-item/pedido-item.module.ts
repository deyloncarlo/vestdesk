import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VestdeskSharedModule } from '../../shared';
import {
    PedidoItemService,
    PedidoItemPopupService,
    PedidoItemComponent,
    PedidoItemDetailComponent,
    PedidoItemDialogComponent,
    PedidoItemPopupComponent,
    PedidoItemDeletePopupComponent,
    PedidoItemDeleteDialogComponent,
    pedidoItemRoute,
    pedidoItemPopupRoute,
} from './';

const ENTITY_STATES = [
    ...pedidoItemRoute,
    ...pedidoItemPopupRoute,
];

@NgModule({
    imports: [
        VestdeskSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PedidoItemComponent,
        PedidoItemDetailComponent,
        PedidoItemDialogComponent,
        PedidoItemDeleteDialogComponent,
        PedidoItemPopupComponent,
        PedidoItemDeletePopupComponent,
    ],
    entryComponents: [
        PedidoItemComponent,
        PedidoItemDialogComponent,
        PedidoItemPopupComponent,
        PedidoItemDeleteDialogComponent,
        PedidoItemDeletePopupComponent,
    ],
    providers: [
        PedidoItemService,
        PedidoItemPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VestdeskPedidoItemModule {}
