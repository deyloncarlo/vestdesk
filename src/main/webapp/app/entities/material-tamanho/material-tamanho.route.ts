import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MaterialTamanhoComponent } from './material-tamanho.component';
import { MaterialTamanhoDetailComponent } from './material-tamanho-detail.component';
import { MaterialTamanhoPopupComponent } from './material-tamanho-dialog.component';
import { MaterialTamanhoDeletePopupComponent } from './material-tamanho-delete-dialog.component';

export const materialTamanhoRoute: Routes = [
    {
        path: 'material-tamanho',
        component: MaterialTamanhoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.materialTamanho.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'material-tamanho/:id',
        component: MaterialTamanhoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.materialTamanho.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const materialTamanhoPopupRoute: Routes = [
    {
        path: 'material-tamanho-new',
        component: MaterialTamanhoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.materialTamanho.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'material-tamanho/:id/edit',
        component: MaterialTamanhoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.materialTamanho.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'material-tamanho/:id/delete',
        component: MaterialTamanhoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.materialTamanho.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
