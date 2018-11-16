import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ConfiguracaoLayoutComponent } from './configuracao-layout.component';
import { ConfiguracaoLayoutDetailComponent } from './configuracao-layout-detail.component';
import { ConfiguracaoLayoutPopupComponent } from './configuracao-layout-dialog.component';
import { ConfiguracaoLayoutDeletePopupComponent } from './configuracao-layout-delete-dialog.component';

export const configuracaoLayoutRoute: Routes = [
    {
        path: 'configuracao-layout',
        component: ConfiguracaoLayoutComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.configuracaoLayout.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'configuracao-layout/:id',
        component: ConfiguracaoLayoutDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.configuracaoLayout.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const configuracaoLayoutPopupRoute: Routes = [
    {
        path: 'configuracao-layout-new',
        component: ConfiguracaoLayoutPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.configuracaoLayout.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'configuracao-layout/:id/edit',
        component: ConfiguracaoLayoutPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.configuracaoLayout.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'configuracao-layout/:id/delete',
        component: ConfiguracaoLayoutDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.configuracaoLayout.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
