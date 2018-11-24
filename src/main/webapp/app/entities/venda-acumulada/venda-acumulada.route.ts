import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { VendaAcumuladaComponent } from './venda-acumulada.component';
import { VendaAcumuladaDetailComponent } from './venda-acumulada-detail.component';
import { VendaAcumuladaPopupComponent } from './venda-acumulada-dialog.component';
import { VendaAcumuladaDeletePopupComponent } from './venda-acumulada-delete-dialog.component';

export const vendaAcumuladaRoute: Routes = [
    {
        path: 'venda-acumulada',
        component: VendaAcumuladaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.vendaAcumulada.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'venda-acumulada/:id',
        component: VendaAcumuladaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.vendaAcumulada.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vendaAcumuladaPopupRoute: Routes = [
    {
        path: 'venda-acumulada-new',
        component: VendaAcumuladaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.vendaAcumulada.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'venda-acumulada/:id/edit',
        component: VendaAcumuladaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.vendaAcumulada.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'venda-acumulada/:id/delete',
        component: VendaAcumuladaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.vendaAcumulada.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
