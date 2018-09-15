import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { UsuarioComponent } from './usuario.component';
import { UsuarioDetailComponent } from './usuario-detail.component';
import { UsuarioPopupComponent } from './usuario-dialog.component';
import { UsuarioDeletePopupComponent } from './usuario-delete-dialog.component';

export const usuarioRoute: Routes = [
    {
        path: 'usuario',
        component: UsuarioComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.usuario.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'usuario/:id',
        component: UsuarioDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.usuario.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const usuarioPopupRoute: Routes = [
    {
        path: 'usuario-new',
        component: UsuarioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.usuario.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'usuario/:id/edit',
        component: UsuarioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.usuario.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'usuario/:id/delete',
        component: UsuarioDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.usuario.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
