import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { LayoutComponent } from './layout.component';
import { LayoutDetailComponent } from './layout-detail.component';
import { LayoutPopupComponent } from './layout-dialog.component';
import { LayoutDeletePopupComponent } from './layout-delete-dialog.component';

export const layoutRoute: Routes = [
    {
        path: 'layout',
        component: LayoutComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.layout.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'layout/:id',
        component: LayoutDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.layout.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const layoutPopupRoute: Routes = [
    {
        path: 'layout-new',
        component: LayoutPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.layout.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'layout/:id/edit',
        component: LayoutPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.layout.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'layout/:id/delete',
        component: LayoutDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.layout.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
