import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { QuantidadeTamanhoComponent } from './quantidade-tamanho.component';
import { QuantidadeTamanhoDetailComponent } from './quantidade-tamanho-detail.component';
import { QuantidadeTamanhoPopupComponent } from './quantidade-tamanho-dialog.component';
import { QuantidadeTamanhoDeletePopupComponent } from './quantidade-tamanho-delete-dialog.component';

export const quantidadeTamanhoRoute: Routes = [
    {
        path: 'quantidade-tamanho',
        component: QuantidadeTamanhoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.quantidadeTamanho.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'quantidade-tamanho/:id',
        component: QuantidadeTamanhoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.quantidadeTamanho.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const quantidadeTamanhoPopupRoute: Routes = [
    {
        path: 'quantidade-tamanho-new',
        component: QuantidadeTamanhoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.quantidadeTamanho.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'quantidade-tamanho/:id/edit',
        component: QuantidadeTamanhoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.quantidadeTamanho.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'quantidade-tamanho/:id/delete',
        component: QuantidadeTamanhoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.quantidadeTamanho.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
