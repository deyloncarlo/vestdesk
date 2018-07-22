import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ModeloComponent } from './modelo.component';
import { ModeloDetailComponent } from './modelo-detail.component';
import { ModeloPopupComponent } from './modelo-dialog.component';
import { ModeloDeletePopupComponent } from './modelo-delete-dialog.component';

export const modeloRoute: Routes = [
    {
        path: 'modelo',
        component: ModeloComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.modelo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'modelo/:id',
        component: ModeloDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.modelo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const modeloPopupRoute: Routes = [
    {
        path: 'modelo-new',
        component: ModeloPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.modelo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'modelo/:id/edit',
        component: ModeloPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.modelo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'modelo/:id/delete',
        component: ModeloDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.modelo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
