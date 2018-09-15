import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ConfiguracaoProdutoComponent } from './configuracao-produto.component';
import { ConfiguracaoProdutoDetailComponent } from './configuracao-produto-detail.component';
import { ConfiguracaoProdutoPopupComponent } from './configuracao-produto-dialog.component';
import { ConfiguracaoProdutoDeletePopupComponent } from './configuracao-produto-delete-dialog.component';

export const configuracaoProdutoRoute: Routes = [
    {
        path: 'configuracao-produto',
        component: ConfiguracaoProdutoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.configuracaoProduto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'configuracao-produto/:id',
        component: ConfiguracaoProdutoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.configuracaoProduto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const configuracaoProdutoPopupRoute: Routes = [
    {
        path: 'configuracao-produto-new',
        component: ConfiguracaoProdutoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.configuracaoProduto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'configuracao-produto/:id/edit',
        component: ConfiguracaoProdutoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.configuracaoProduto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'configuracao-produto/:id/delete',
        component: ConfiguracaoProdutoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.configuracaoProduto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
