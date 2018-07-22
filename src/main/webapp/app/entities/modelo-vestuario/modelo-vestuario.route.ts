import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ModeloVestuarioComponent } from './modelo-vestuario.component';
import { ModeloVestuarioDetailComponent } from './modelo-vestuario-detail.component';
import { ModeloVestuarioPopupComponent } from './modelo-vestuario-dialog.component';
import { ModeloVestuarioDeletePopupComponent } from './modelo-vestuario-delete-dialog.component';

export const modeloVestuarioRoute: Routes = [
    {
        path: 'modelo-vestuario',
        component: ModeloVestuarioComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.modeloVestuario.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'modelo-vestuario/:id',
        component: ModeloVestuarioDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.modeloVestuario.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const modeloVestuarioPopupRoute: Routes = [
    {
        path: 'modelo-vestuario-new',
        component: ModeloVestuarioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.modeloVestuario.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'modelo-vestuario/:id/edit',
        component: ModeloVestuarioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.modeloVestuario.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'modelo-vestuario/:id/delete',
        component: ModeloVestuarioDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.modeloVestuario.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
