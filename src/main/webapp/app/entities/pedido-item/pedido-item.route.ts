import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PedidoItemComponent } from './pedido-item.component';
import { PedidoItemDetailComponent } from './pedido-item-detail.component';
import { PedidoItemPopupComponent } from './pedido-item-dialog.component';
import { PedidoItemDeletePopupComponent } from './pedido-item-delete-dialog.component';

export const pedidoItemRoute: Routes = [
    {
        path: 'pedido-item',
        component: PedidoItemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.pedidoItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pedido-item/:id',
        component: PedidoItemDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.pedidoItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pedidoItemPopupRoute: Routes = [
    {
        path: 'pedido-item-new',
        component: PedidoItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.pedidoItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pedido-item/:id/edit',
        component: PedidoItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.pedidoItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pedido-item/:id/delete',
        component: PedidoItemDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.pedidoItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
