import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EtapaProducaoComponent } from './etapa-producao.component';
import { EtapaProducaoDetailComponent } from './etapa-producao-detail.component';
import { EtapaProducaoPopupComponent } from './etapa-producao-dialog.component';
import { EtapaProducaoDeletePopupComponent } from './etapa-producao-delete-dialog.component';

export const etapaProducaoRoute: Routes = [
    {
        path: 'etapa-producao',
        component: EtapaProducaoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.etapaProducao.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'etapa-producao/:id',
        component: EtapaProducaoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.etapaProducao.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const etapaProducaoPopupRoute: Routes = [
    {
        path: 'etapa-producao-new',
        component: EtapaProducaoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.etapaProducao.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'etapa-producao/:id/edit',
        component: EtapaProducaoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.etapaProducao.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'etapa-producao/:id/delete',
        component: EtapaProducaoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.etapaProducao.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
