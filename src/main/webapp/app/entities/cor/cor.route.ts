import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CorComponent } from './cor.component';
import { CorDetailComponent } from './cor-detail.component';
import { CorPopupComponent } from './cor-dialog.component';
import { CorDeletePopupComponent } from './cor-delete-dialog.component';

export const corRoute: Routes = [
    {
        path: 'cor',
        component: CorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.cor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'cor/:id',
        component: CorDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.cor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const corPopupRoute: Routes = [
    {
        path: 'cor-new',
        component: CorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.cor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cor/:id/edit',
        component: CorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.cor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cor/:id/delete',
        component: CorDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.cor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
