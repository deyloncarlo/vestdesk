import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { RelatorioVendaComponent } from './relatorio-venda.component';

export const relatorioVendaRoute: Routes = [
    {
        path: 'relatorio-venda',
        component: RelatorioVendaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.vendaAcumulada.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const relatorioVendaPopupRoute: Routes = [
    // {
    //     path: 'venda-acumulada-new',
    //     component: VendaAcumuladaPopupComponent,
    //     data: {
    //         authorities: ['ROLE_USER'],
    //         pageTitle: 'vestdeskApp.vendaAcumulada.home.title'
    //     },
    //     canActivate: [UserRouteAccessService],
    //     outlet: 'popup'
    // },
    // {
    //     path: 'venda-acumulada/:id/edit',
    //     component: VendaAcumuladaPopupComponent,
    //     data: {
    //         authorities: ['ROLE_USER'],
    //         pageTitle: 'vestdeskApp.vendaAcumulada.home.title'
    //     },
    //     canActivate: [UserRouteAccessService],
    //     outlet: 'popup'
    // },
    // {
    //     path: 'venda-acumulada/:id/delete',
    //     component: VendaAcumuladaDeletePopupComponent,
    //     data: {
    //         authorities: ['ROLE_USER'],
    //         pageTitle: 'vestdeskApp.vendaAcumulada.home.title'
    //     },
    //     canActivate: [UserRouteAccessService],
    //     outlet: 'popup'
    // }
];
