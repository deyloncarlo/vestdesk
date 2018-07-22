import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { FormaPagamentoComponent } from './forma-pagamento.component';
import { FormaPagamentoDetailComponent } from './forma-pagamento-detail.component';
import { FormaPagamentoPopupComponent } from './forma-pagamento-dialog.component';
import { FormaPagamentoDeletePopupComponent } from './forma-pagamento-delete-dialog.component';

export const formaPagamentoRoute: Routes = [
    {
        path: 'forma-pagamento',
        component: FormaPagamentoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.formaPagamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'forma-pagamento/:id',
        component: FormaPagamentoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.formaPagamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const formaPagamentoPopupRoute: Routes = [
    {
        path: 'forma-pagamento-new',
        component: FormaPagamentoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.formaPagamento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'forma-pagamento/:id/edit',
        component: FormaPagamentoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.formaPagamento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'forma-pagamento/:id/delete',
        component: FormaPagamentoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.formaPagamento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
