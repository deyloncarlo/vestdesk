import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AdiantamentoComponent } from './adiantamento.component';
import { AdiantamentoDetailComponent } from './adiantamento-detail.component';
import { AdiantamentoPopupComponent } from './adiantamento-dialog.component';
import { AdiantamentoDeletePopupComponent } from './adiantamento-delete-dialog.component';

export const adiantamentoRoute: Routes = [
    {
        path: 'adiantamento',
        component: AdiantamentoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.adiantamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'adiantamento/:id',
        component: AdiantamentoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.adiantamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const adiantamentoPopupRoute: Routes = [
    {
        path: 'adiantamento-new',
        component: AdiantamentoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.adiantamento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'adiantamento/:id/edit',
        component: AdiantamentoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.adiantamento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'adiantamento/:id/delete',
        component: AdiantamentoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.adiantamento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
