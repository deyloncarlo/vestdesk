import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ClienteComponent } from './cliente.component';
import { ClienteDetailComponent } from './cliente-detail.component';
import { ClientePopupComponent } from './cliente-dialog.component';
import { ClienteDeletePopupComponent } from './cliente-delete-dialog.component';

export const clienteRoute: Routes = [
    {
        path: 'cliente',
        component: ClienteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.cliente.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'cliente/:id',
        component: ClienteDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.cliente.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clientePopupRoute: Routes = [
    {
        path: 'cliente-new',
        component: ClientePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.cliente.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cliente/:id/edit',
        component: ClientePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.cliente.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cliente/:id/delete',
        component: ClienteDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.cliente.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
