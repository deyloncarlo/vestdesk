import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ResumoEstoqueComponent } from './resumo-estoque.component';

export const resumoEstoqueRoute: Routes = [
    {
        path: 'resumo-estoque',
        component: ResumoEstoqueComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vestdeskApp.resumoEstoque.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];